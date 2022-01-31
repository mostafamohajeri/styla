package prolog.terms
import prolog.interp.Prog
import prolog.io.IO
import scala.collection.mutable.LinkedHashSet

class Fun(sym: String, var args: Array[Term], source: Any = None) extends Const(sym,source) {
  def this(sym: String) = this(sym, null)

  override def len = args.length



  final def init(arity: Int) {
    this.args = new Array[Term](arity)
    for (i <- 0 until arity) {
      args(i) = new Var()
    }
  }

  final def getArg(i: Int) = args(i).ref

  final def putArg(i: Int, t: Term, p: Prog) = {
    val oldtop = p.trail.size
    if (args(i).ref.unify(t, p.trail)) 1
    else {
      p.trail.unwind(oldtop)
      0
    }
  }

  override def unify(term: Term, trail: Trail): Boolean = {
    val that = term.ref
    if (bind_to(that, trail)) {
      val other = that.asInstanceOf[Fun]
      val l = args.length
      if (other.args.length != l) return false
      for (i <- 0 until l) {
        if (!args(i).ref.unify(other.args(i), trail))
          return false;
      }
      return true;
    } else
      return that.bind_to(this, trail)
  }

  // stuff allowing polymorphic cloning of Fun subclasses
  // without using reflection - should be probably faster than
  // reflection classes - to check

  final def funClone(): Fun = {
    try {
      // use of clone is needed for "polymorphic" copy 
      clone().asInstanceOf[this.type]
    } catch {
      case _: Error => {
        IO.warnmes("funcClone failed on" + this)
        null
      }
    }
  }

  override def tcopy(dict: Copier): Fun = {

    val t = funClone()
    t.args = new Array[Term](args.length)
    for (i <- 0 until args.length) {
      t.args(i) = args(i).tcopy(dict)
    }
    t
  }

  override def vcollect(dict: LinkedHashSet[Term]) {
    for (i <- 0 until args.length) {
      args(i).vcollect(dict)
    }
  }

  /*
  override def ucopy(term: Term, dict: Copier, trail: Trail): Fun = {
    val that = term.ref
    if (that.isInstanceOf[Var]) {
      val t = funClone()
      t.args = new Array[Term](args.length)
      for (i <- 0 until args.length) {
        t.args(i) = args(i).tcopy(dict)
      }
      t
    } else if (unify(that, trail)) that.asInstanceOf[Fun]
    else null
  }
  */

  override def toString = {
    val s = new StringBuffer
    s.append(name)
    s.append('(')
    if (args.eq(null)) {
      s.append("...null...")
    } else {
      s.append(getArg(0))
      for (i <- 1 until args.length) {
        s.append("," + getArg(i))
      }
    }
    s.append(')')
    s.toString
  }

}
