package prolog.terms
import prolog.builtins.true_

final class Clause(h: Term, b: Term)
  extends Fun(":-", Array(h, b)) {

  def getHead = args(0).ref
  def getBody = args(1).ref

  override def toString = Clause.to_string(this)
}

object Clause {
  final def build(x: Term, y: Term) = new Clause(x, y)

  final def fromList(xs: List[Term]): Clause = {
    if (xs.tail.isEmpty) build(xs.head, true_())
    else build(xs.head, Conj.fromList(xs.tail))
  }

  final def toList(c: Clause): List[Term] =
    List(c.getHead) ++ Conj.toList(c.getBody)

  final def toClauseList(t: Term): List[Term] = t match {
    case c: Clause => toList(c)
    case _ => List(t)
  }

  def to_string(t: Fun) = t.getArg(0) + ":-" + t.getArg(1)
}