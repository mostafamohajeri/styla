package prolog.fluents

//import java.io._
import scala.collection.mutable._

import prolog.terms._
import prolog.interp.Prog
import prolog.io._

case class Key(f: String, n: Int)

class DataBase(fname: String) extends LinkedHashMap[Key, Deque[List[Term]]] {
  val vars = new LinkedHashMap[String, Var]
  def this() = this(null)

  type CLAUSE = List[Term]
  if (null != fname) {
    fromFile(fname, true)
  } else
    initialize()

  private final def initialize() {
    clear()
    addAll(DataBase.lib)
    vars.clear()
    vars ++= DataBase.vars
  }

  final def fromFile(f0: String, doClear: Boolean): Int = {
    val f = IO.find_file(f0)
    if (f.eq(null)) 0
    else {
      if (doClear) initialize()
      val parser = new TermParser(vars)
      val cs = parser.file2clauses(f)
      addAll(cs)
      1
    }
  }

  def addAll(cs: List[CLAUSE]) {
    cs.foreach(add_or_exec)
  }

  def exec_cmd(body: CLAUSE) = {
    val q = new_prog(Const.cmd, body)
    q.toSink(new EmptySink())
  }

  def add_or_exec(c: CLAUSE): Boolean = {
    c match {
      case null => false
      case Nil => false
      case Const.cmd :: b :: bs => {
        b match {
          case xs: Cons =>
            if (bs.isEmpty)
              Cons.toList(xs).foreach {
                f => fromFile(f.asInstanceOf[Const].name, false)
              }
            else
              IO.warnmes("bad directive: " + c)

          case other => exec_cmd(b :: bs)
        }
        true
      }
      case other => {
        add(other)
        true
      }
    }
  }

  def new_prog(answer: Term, gs: List[Term]): Prog = {
    val q = new Prog(this)
    q.set_query(answer, gs)
    q
  }

  def key(c: CLAUSE) = {
    val x = c.head.ref.asInstanceOf[Const]
    Key(x.sym, x.len)
  }

  def key(c: Const) = {
    val x = c.ref.asInstanceOf[Const]
    Key(x.sym, x.len)
  }

  def has_clauses(h: Term): Int = {
    val x = h.ref.asInstanceOf[Const]
    val k = Key(x.sym, x.len)
    val r = get(k)
    r match {
      case None => -1
      case Some(cs) => if (cs.isEmpty) 0 else 1
    }
  }

  def add(c: CLAUSE) {
    val k = key(c)
    val cs = getOrElseUpdate(k, new Deque[CLAUSE]())
    cs.add(c)
  }

  def push(c: CLAUSE) {
    val k = key(c)
    val cs: Deque[CLAUSE] =
      getOrElseUpdate(k, new Deque[CLAUSE]())
    cs.push(c)
  }

  def del1(h: Term): List[Term] = {
    val c = List(h)
    val k = key(c)
    val xss = get(k)

    xss match {
      case None => null
      case Some(css) => {
        val trail = new Trail()
          def is_matching(cs: CLAUSE) = h.matches(cs.head, trail)
        val maybeCs = css.dequeueFirst(is_matching)
        maybeCs match {
          case None => null
          case Some(cs) => cs // .head
        }
      }
    }
  }

  def delAll(h: Term) {
    while (null != del1(h)) {}
  }

  def cleanUpKey(h: Term) {
    delAll(h)
    val k = key(List(h))
    val cs = get(k)
    cs match {
      case None => {
        this -= k
      }
      case Some(x) => {
        if (x.isEmpty) this -= k
      }
      case _ => {}
    }
  }

  def getMatches(c: CLAUSE): Deque[CLAUSE] = {
    getMatches(c, true)
  }

  def getMatches(c: CLAUSE, verbose: Boolean): Deque[CLAUSE] = {
    val k = key(c)
    get(k) match {
      case None => {
        if (verbose) IO.warnmes("call to undefined predicate: " + k)
        null
      }
      case Some(cs) => cs
    }
  }

  def revVars() = {
    val I = vars.iterator
    val revMap = new LinkedHashMap[Var, String]
    while (I.hasNext) {
      val (s, v) = I.next
      val s_ = if (s.startsWith("__")) "_" else s
      revMap.put(v, s_)
    }
    revMap
  }
}

object DataBase {
  val parser = new TermParser()
  val vars = parser.vars
  val lib = parser.parseProg(Lib.code)

  def key2fun(k: Key) =
    new Fun("/", Array(new Const(k.f), SmallInt(k.n)))

  def fun2key(fn: Fun) = {
    new Key(fn.getArg(0).asInstanceOf[Const].name,
      fn.getArg(1).asInstanceOf[Num].getValue.intValue)
  }
}