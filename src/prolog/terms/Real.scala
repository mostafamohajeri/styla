package prolog.terms

case class Real(nval: BigDecimal) extends Num {

  def this(s: String) = this(BigDecimal(s))
  def this(n: BigInt) = this(BigDecimal(n))
  def this(n: Int) = this(BigDecimal(n))

  override def name = nval.toString

  override def getValue = nval

  /*
  override def bind_to(that: Term, trail: Trail) =
    super.bind_to(that, trail) &&
      this.nval == that.asInstanceOf[Real].nval
  */
}