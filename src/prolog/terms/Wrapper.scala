package prolog.terms

case class Wrapper(val o: Any) extends SystemObject {
  def this() = this(null)
  override def name = "{'" + (if (null == o) getClass() else o) + "'}"
}
