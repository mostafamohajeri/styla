package prolog.fluents

import scala.collection.mutable._
import prolog.terms._

class EmptySink extends TermSink {
  override def putElement(x: Term) = {
    if (x.eq(null)) 0 else 1
  }
  override def stop {}
  override def collect = Const.nil
}