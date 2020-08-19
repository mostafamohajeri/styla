package prolog.terms

class Truth(val truth: Boolean) extends Nonvar {
  override def name = truth.toString
  def len: Int = 0

  override def bind_to(that: Term, trail: Trail) =
    super.bind_to(that, trail) &&
      truth == that.asInstanceOf[Truth].truth

}



