package prolog.terms
import prolog.interp.Prog
import scala.collection.mutable.LinkedHashSet

abstract class Term extends Cloneable {
  def ref = this
  def bind_to(that: Term, trail: Trail): Boolean

  def unify(that: Term, trail: Trail): Boolean

  def tcopy(dict: Copier) = this

  def copy = tcopy(new Copier())

  def vcollect(dict: LinkedHashSet[Term]) {
  }

  def vars_of = {
    val dict = new LinkedHashSet[Term]()
    vcollect(dict)
    Cons.fromIterator(dict.iterator)
  }

  def var_array_of = {
    val dict = new LinkedHashSet[Term]()
    vcollect(dict)
    dict.toArray
  }

  final def matches(that: Term, trail: Trail): Boolean = {
    val oldtop = trail.size
    val ok = unify(that, trail)
    trail.unwind(oldtop)
    ok
  }

  /*
  def ucopy(other: Term, dict: Copier, trail: Trail): Term = {
    val that = other.ref
    if (this == that) that else null
  }

  def unify_or_build_with(g: Term, copier: Copier, trail: Trail): Term = {
    val oldtop = trail.size
    val c = ucopy(g, copier, trail)
    if (c.eq(null)) {
      trail.unwind(oldtop)
      null
    } else g
  }
  */

  final def exec() = -1
  def exec(p: Prog) = -1

}

object Term {

  /*
  def unapply(x: Term, y: Term): Option[Term] = {

    val trail = new Trail()
    val ok = x.unify(y, trail)
    if (!ok) trail.unwind(0)
    if (ok) Some(y) else None
  }
  */

  final def copyList(ts: List[Term], copier: Copier): List[Term] =
    {
      val dict = new Copier()
      ts.map(t => t.tcopy(dict))
    }

  def tcompare(a: Term, b: Term): Int = {
    (a.ref, b.ref) match {
      case (x: Var, y: Var) => math.signum(x.hashCode - y.hashCode)
      case (v: Var, n: Nonvar) => -1
      case (v: Nonvar, n: Var) => 1
      case (x: Num, y: Num) => math.signum(x.getValue.compare(y.getValue))
      case (x: Num, y: Const) => -1
      case (x: Const, y: Num) => 1
      case (f: Fun, g: Fun) => {
        val d = f.args.length - g.args.length
        if (d != 0) d
        else {
          val e = f.sym.compare(g.sym)
          if (e != 0) e
          else {
            val l = f.args.length
            var r = 0
            var i = 0
            while (r == 0 && i < l) {
              r = tcompare(f.args(i), g.args(i))
              i += 1
            }
            r
          }
        }
      }
      case (c: Const, f: Fun) => -1
      case (f: Fun, c: Const) => 1
      case (c: Const, d: Const) => {
        math.signum(c.sym.compare(d.sym))
      }
      case (l:Any,r:Any) => throw new RuntimeException(f"compare not implemented between $l and $r")
    }
  }

}