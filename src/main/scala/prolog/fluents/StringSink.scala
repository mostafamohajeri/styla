package prolog.fluents

import scala.collection.mutable._
import prolog.terms._

class StringSink extends TermSink {
  var s = new StringBuilder()
  def putElement(x: Term) = {
    if (s.eq(null)) 0
    else {
      s.append(x)
      1
    }
  }
  def stop() { s = null }
  def collect = new Const(s.toString)
}