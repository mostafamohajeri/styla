package prolog.fluents
import prolog.terms._

class IntegerSource(var fuel: Int, a: Int, var x: Int, b: Int) extends TermSource {

  def getElement() = {
    if (fuel <= 0) null
    else {
      val R = SmallInt(x)
      x = a * x + b
      fuel -= 1
      R
    }
  }

  override def stop() = (fuel = 0)

  override def toString() =
    "{(x->" + a + "*x+" + b + ")[" + fuel + "]=" + x + "}"
}