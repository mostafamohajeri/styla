package prolog.terms
import prolog.builtins.true_

final class Conj(h: Term, b: Term)
  extends Fun(",", Array(h, b)) {

  def getHead = args(0).ref
  def getBody = args(1).ref

  override def toString = Conj.to_string(this)
}

object Conj {

  final def build(x: Term, y: Term) = new Conj(x, y)

  final def fromList(xs: List[Term]): Term = xs match {
    case Nil => true_()
    case _ => xs.reduceRight[Term](build)
  }
  final def toList(t: Term): List[Term] = t match {
    case x: Conj => x.getHead :: toList(x.getBody)
    case x: true_ => Nil
    case x: Term => List(x)
  }

  def to_string(t: Fun) = t.getArg(0) + "," + t.getArg(1)
}