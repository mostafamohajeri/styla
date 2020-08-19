package prolog.terms

abstract class TermSource extends Fluent with Iterator[Term] {
  def getElement(): Term

  var last: Term = null
  var taken = false
  var isAlive = true

  def hasNext = {
    if (!isAlive) false
    else if (!taken) true
    else {
      last = getElement()
      isAlive = !next.eq(null)
      if (isAlive) taken = false
      isAlive
    }
  }

  def next() = {
    taken = true
    last
  }

  def toList(toCopy: Boolean): Term = {
    var head = getElement()
    if (null == head)
      return Const.nil
    if (toCopy) head = head.copy
    val l = new Cons(head, Const.nil)
    var curr = l
    var more = true
    while (more) {
      head = getElement()
      if (null == head)
        more = false
      else {
        if (toCopy) head = head.copy
        val tail = new Cons(head, Const.nil)
        curr.args(1) = tail
        curr = tail;
      }
    }
    return l
  }

  def toSink(to: TermSink) {
    var more = true
    while (more) {
      val x = getElement()
      if (x.eq(null)) {
        to.stop()
        stop()
        more = false
      } else {
        to.putElement(x)
      }
    }
  }

  override def stop() {
    last = null
    taken = false
    isAlive = false
  }
}