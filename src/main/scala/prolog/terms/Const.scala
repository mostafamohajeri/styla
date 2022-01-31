package prolog.terms

class Const(val sym: String, source: Any = None) extends Nonvar {
  override def name = sym
  def len: Int = 0

  override def bind_to(that: Term, trail: Trail) =
    super.bind_to(that, trail) &&
      sym == that.asInstanceOf[Const].sym

}

object Const {
  final val no = new Const("no")
  final val yes = new Const("yes")
  final val nil = new Const("[]")
  final val cmd = new Const("$cmd")
  final def the(X: Term): Const =
    if (X.eq(null)) Const.no
    else {
      val the = new Fun("the", Array[Term](X))
      the.copy.asInstanceOf[Const]
    }
}

