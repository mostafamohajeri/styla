package prolog.terms
import prolog.builtins.fail_
import prolog.io.TermParser

final class Disj(h: Term, b: Term)
  extends Fun(";", Array(h, b)) {

  def getHead = args(0).ref
  def getBody = args(1).ref

  override def toString = Disj.to_string(this)
}

object Disj {

  final def build(x: Term, y: Term) = new Disj(x, y)

  final def fromList(xs: List[Term]): Term = xs match {
    case Nil => fail_()
    case _ => xs.reduceRight[Term](build)
  }

  final def toList(t: Term): List[Term] = t match {
    case x: Disj => x.getHead :: toList(x.getBody)
    case x: fail_ => Nil
    case x: Term => List(x)
  }

  def to_string(t: Fun) =
    TermParser.term2string(t.getArg(0)) +
      ";" + TermParser.term2string(t.getArg(1))
}