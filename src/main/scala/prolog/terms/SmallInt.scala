package prolog.terms

final case class SmallInt(nval: Int) extends Num {

  def this(s: String) = this(s.toInt)

  override def name = nval.toString

  override def getValue = BigDecimal(nval)

  /*
  override def bind_to(that: Term, trail: Trail) =
    super.bind_to(that, trail) && this.nval == that.asInstanceOf[SmallInt].nval
  */
}