package prolog.fluents

import scala.collection.mutable._
import prolog.terms._

class IteratorSource(var i: Iterator[Any]) extends TermSource {

  override def getElement(): Term = {
    if (null == i) null
    else if (!i.hasNext) {
      i = null
      null
    } else {
      i.next match {
        case t: Term => t
        case s: String => new Const(s)
        case c: Char => new Const(c.toString)
        case i: Int => SmallInt(i)
        case n: BigDecimal => Real(n)
        case other => new Wrapper(other)
      }
    }
  }

  override def stop() {
    i = null
  }
}