package prolog.tests

sealed abstract class T
case object E extends T
case class O[A <: T](x: A) extends T
case class I[A <: T](x: A) extends T

object S {
  def apply(x: T) = s(x)
  def unapply(x: T) = p(x)

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

object T extends App {
  val a: T = S(S(E))
  println(a)
  /*
  a match {
   case S(x) => println(x)
    case _ => println("nothing")
  }
  */
}