package prolog.tests
import prolog._
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

class code extends Prog {
  def get_fun(name: String, arity: Int, v: Var, p: Prog): Boolean = {
    val ok = v.ref match {
      case x: Var => {
        val f = new Fun(name)
        f.init(arity)
        x.bind_to(f, p.trail)
        true
      }
      case x: Fun => x.len == arity && name == x.name
      case _ => false
    }
    ok
  }

  def get_arg(i: Int, v: Var, a: Var, p: Prog): Boolean = {
    //println(i + ":" + v + ":" + a)
    val ok = v.ref match {
      case x: Fun => {
        //println("good" + x.args(i) + "=" + a)
        val r = a.unify(x.args(i), p.trail)
        //Console.println("r=" + r)
        r
      }
      case other => {
        //println("bad" + other)
        false
      }
    }
    ok
  }

  def get_const(name: String, a: Var, p: Prog): Boolean = {
    a.ref match {
      case c: Const => name == c.name
      case x: Var => x.bind_to(new Const(name), p.trail)
      case _ => false
    }
  }
}

object code extends App {
  println("add tests here")
  val p = new Prog()
  val c = new code()
  def cls(h: Var, b: Var): Boolean = {
    val oldtop = p.trail.size
    val s1 = "[]"
    val v1 = new Var()
    val v2 = new Var()
    val v3 = new Var()
    val r =
      c.get_fun("app0", 3, h, p) &&
        c.get_arg(0, h, v1, p) &&
        c.get_const("[]", v1, p) &&
        c.get_arg(1, h, v2, p) &&
        c.get_arg(2, h, v3, p)
    if (!r) p.trail.unwind(oldtop)
    r
  }

  val h = new Var()
  val b = new Var()
  val r = cls(h, b)

  println(h)
}
