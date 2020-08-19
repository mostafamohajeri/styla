package prolog.terms

import scala.collection.mutable
import scala.collection.mutable.ArrayStack
// AWFUL PERFORMANCE BUG if using import scala.collection.imutable.Stack

class ObjectStack[Term] extends mutable.Stack[Term] {
  override def toString = {
    val i = iterator
    val s = new StringBuilder()
    var k = size
    while (i.hasNext) {
      k -= 1
      s.append("[" + k + "]=>")
      s.append(i.next)
      s.append("\n")
    }
    s.toString
  }

}
