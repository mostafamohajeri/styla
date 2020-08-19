package prolog.terms

abstract class Num extends Nonvar {

  override def toString(): String = name

  def getValue: BigDecimal

  override def bind_to(that: Term, trail: Trail) =
    that.isInstanceOf[Num] && that.asInstanceOf[Num].getValue ==
      this.getValue

}
