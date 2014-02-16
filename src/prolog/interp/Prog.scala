package prolog.interp

import prolog.fluents.DataBase
import prolog.io.IO
import prolog.terms._

/**
 * The class Prog implements instances of Prolog interpreters, wrapped up as self-contained
 * first-class LogicEngines, usable as Scala or Akka actors. It is parameterized by a
 * DataBase object containing Prolog clauses. A default library encapsulated
 * as a Scala class in file Lib.scala is added automatically when a DataBase object is
 * created by a TermParser from a source file.
 * 
 * A Prog uses two stacks. 
 * 
 * The orStack is implemented as a mutable ObjectStack that
 * handles backtracking.
 * 
 * A List-represented goal stack that contains the current
 * resolvent for a given choice. 
 * 
 * The goal-stack is pushed/popped as needed from the
 * the orStack but being represented as an immutable List its tail is actually shared
 * so this operation is quite efficient. The underlying model is the unfolding of a
 * predicate head present in a goal atom into its body. The clause, once matched, is copied
 * and its body occupies the top of the goal stack. As variables on the goal stack end up
 * being shared between alternative or branches, a trail, unique to a Prog is used to
 * record binding of variables that ere undone on backtracking. This is handled 
 * by the related Unfolder class, which also executed built-in sequences hanging between
 * genuine Prolog predicates.
 * 
 * All built-ins are deterministic, implemented as Scala functions and as such 
 * no backtracking is implemented within them, except that they trail variable bindings.
 * As they are never pushed on a stack, they are incur no space overhead.
 * 
 * To speed-up built-in calls, a simple reflection mechanism is used at parsing time
 * when their string names are associated to the corresponding Scala objects and then
 * cached in a Map. The convention is that all built-ins are hosted in the package
 * prolog.builtins. This allows the parser to instantly recognize them, without any
 * glue code needed for calling them at runtime through an exec() method.
 * 
 */

class Prog(val db: DataBase) extends TermSource {
  def this() = this(new DataBase(null))
  def this(fname: String) = this(new DataBase(fname))

  type CLAUSE = List[Term]
  type GOAL = List[Term]

  val trail = new Trail()
  val orStack = new ObjectStack[Unfolder]()
  val copier = new Copier()

  private var answer: Term = null
  private var query: GOAL = null

  var isStopped = true
  private var justBuiltins = false
  var popped = true

  def set_query(answer: Term, query: GOAL) {
    orStack.clear()
    trail.clear()

    this.query = query
    this.answer =
      if (!answer.eq(null)) answer
      else {
        // if goal is just a conjunct, this.answer is that conjunct
        val conj: Term = query match {
          case Nil => { stop(); null }
          case head :: Nil => head
          case xs => {
            Conj.fromList(xs)
          }
        }
        conj
      }

    val topres = pushUnfolder(query)
    // return from cmd line is ignored

    if (!topres._1.eq(null)) {
      isStopped = false
      justBuiltins = topres._1.isEmpty
    }
  }

  def set_query(query: GOAL) {
    set_query(null, query: GOAL)
  }

  def pushUnfolder(g0: GOAL): (GOAL, Term) = {
    val g = reduceBuiltins(g0)
    var return_term: Term = null

    if (!g.eq(null) && !g.isEmpty) {
      g.head match {
        // we assume the stub return(_) is defined
        case f: Answer =>
          return_term = f.getArg(0)
        case _ => ()
      }

      val cs = db.getMatches(g)
      if (!cs.eq(null)) {
        val u = new Unfolder(this, g, cs.iterator)
        orStack.push(u)
      }
    }

    (g, return_term)
  }

  private def reduceBuiltins(goal: GOAL): GOAL = {
    if (null == goal) null
    else {
      var newgoal = goal
      if (!newgoal.isEmpty) {
        var ret = 1
        while (ret > 0 && !newgoal.isEmpty) {
          var b: Term = null
          try {
            b = newgoal.head.ref
            ret = b.exec(this)
          } catch {
            case err: Error => {
              //println("error=" + err)
              //ret = IO.errmes(err.getLocalizedMessage() +
              ret = IO.errmes(err +
                ",\n*** bad arguments in built-in", b, this)
            }
          }
          if (ret >= 0) newgoal = newgoal.tail
        }
        if (0 == ret) newgoal = null
      }
      newgoal
    }
  }

  override def getElement(): Term = {
    if (isStopped) return null

    if (justBuiltins) {
      stop()
      return answer
    }

    var newgoal: GOAL = null
    var more = true
    while (more && !orStack.isEmpty) {
      val step: Unfolder = orStack.top
      //println("step="+step)
      newgoal = step.nextGoal()

      if (step.lastClause && !orStack.isEmpty) {
        orStack.pop()
        popped = true
      } else
        popped = false

      val res = pushUnfolder(newgoal)

      if (null != res._2) return res._2
      newgoal = res._1

      if (Nil == newgoal) more = false
      // "no push when null" eventually stops it but only when orStack.isEmpty
    }

    if (newgoal.eq(null)) {
      stop()
      return null
    }

    if (query.isEmpty) stop()
    answer
  }

  override def stop() {
    super.stop()
    if (isStopped) trail.unwind(0)
    else {
      //if (null != trail) trail.unwind(0)
      orStack.clear()
      isStopped = true
    }
  }
}

object Prog extends Prog {
  def make_db(files: Term, p: Prog) = {
    if (Const.nil == files) p.db
    else {
      val file: Term = files.asInstanceOf[Cons].getHead
      val fname: String = file.asInstanceOf[Const].sym
      new DataBase(fname)
    }
  }

  def init_with(db: DataBase, x: Term, g: Term, q: Prog) = {
    val gs0 = x :: Conj.toList(g)
    val gs = Term.copyList(gs0, new Copier())
    q.set_query(gs.head, gs.tail)
  }
}
