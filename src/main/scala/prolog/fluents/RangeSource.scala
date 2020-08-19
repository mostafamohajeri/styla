package prolog.fluents

import prolog.terms._

class RangeSource(low: Int, high: Int) extends TermSource {

  var current = low;
  def getElement() = {
    if (current >= high) null
    else {
      val R = SmallInt(current)
      current += 1
      R
    }
  }

  override def stop() = (current = high)

  override def toString() =
    "???"
}