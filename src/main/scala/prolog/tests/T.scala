package prolog.tests

trait T {
  def s(a: T): T = a match {
    case E => O(E)
    case O(b) => I(b)
    case I(b) => O(s(b))
  }

  def p(a: T): T = a match {
    case E => null
    case I(b) => O(b)
    case O(b) => I(p(b))
  }
}
case object E extends T
case class O[A <: T](x: A) extends T
case class I[A <: T](x: A) extends T

object S extends T {
  def apply(x: T) = s(x)
  def unapply(x: T) = x match {
    case E => None
    case a => Some(p(a))
  }
}

object T extends App {
  val a: T = S(S(E))
  println(a)

  a match {
    case S(x) => println(x)
    case _ => println("nothing")
  }

}
