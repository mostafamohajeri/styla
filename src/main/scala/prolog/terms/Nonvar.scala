package prolog.terms

abstract class Nonvar extends Term {
  def name: String

  override def bind_to(that: Term, trail: Trail) =
    getClass.eq(that.getClass)

  override def unify(other: Term, trail: Trail): Boolean = {
    val that = other.ref
    if (bind_to(that, trail)) true
    else that.bind_to(this, trail)
  }

  override def toString = name
}