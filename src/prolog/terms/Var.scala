package prolog.terms
import scala.collection.mutable.LinkedHashSet

class Var() extends Term {
  private var value: Term = this

  final def unbound = this.eq(value)

  override def ref: Term =
    if (unbound) this else value.ref

  override final def bind_to(x: Term, trail: Trail) = {
    value = x
    trail.push(this)
    true
  }

  final def set_to(t: Term) {
    value = t.ref
  }

  final def unify(other: Term, trail: Trail) = {
    val that = other.ref
    val myref = ref
    if (myref.eq(that)) true
    else
      myref.bind_to(that, trail)
  }

  final def undo() = value = this

  override def tcopy(dict: Copier): Term = {
    val root = ref
    if (root == this) dict.getOrElseUpdate(this, new Var())
    else root.tcopy(dict)
  }

  override def vcollect(dict: LinkedHashSet[Term]) {
    val root = ref
    if (root == this) dict += this
    else root.vcollect(dict)
  }

  /*
  override def ucopy(g: Term, dict: Copier, trail: Trail): Term = {
    val root = ref
    val c: Term =
      if (root == this)
        dict.getOrElseUpdate(this, new Var())
      else
        root.ucopy(g, dict, trail)
    if (c.eq(null)) null
    else if (c.unify(g, trail)) g
    else null
  }
  */

  override def toString = {
    if (unbound) {
      val h: Long = hashCode
      val n: Long = if (h < 0) 2 * (-h) + 1 else 2 * h
      "_" + n
    } else ref.toString
  }
}