package prolog.terms

final class Cons(h: Term, b: Term)
  extends Fun(".", Array(h, b)) {

  def getHead = args(0).ref
  def getBody = args(1).ref

  override def toString =
    Cons.to_string({ x => x.toString }, this)
}

object Cons {
  final def build(x: Term, y: Term) = new Cons(x, y)

  final def fromList(xs: List[Term]): Term = xs match {
    case Nil => Const.nil
    case _ => xs.foldRight[Term](Const.nil)(build)
  }

  final def fromIterator(xs: Iterator[Term]): Term = {
    //case Nil => Const.nil
    //case _ => xs.foldRight[Term](Const.nil)(build)
    if (!xs.hasNext) Const.nil
    else build(xs.next, fromIterator(xs))
  }

  final def toList(t: Term): List[Term] = t match {
    case Const.nil => Nil
    case x: Cons => x.getHead :: toList(x.getBody)
    //case x: Term => List(x)
  }

  def to_string(f: Term => String, c: Cons) = {
    val s = new StringBuffer()
    s.append("[")
    var x: Term = c
    var more = true
    while (more) {
      val c: Cons = x.asInstanceOf[Cons]
      s.append(f(c.getHead))
      x = c.getBody
      if (x.isInstanceOf[Cons])
        s.append(",")
      else {
        more = false
        if (x != Const.nil) {
          s.append("|")
          s.append(f(x))
        }
      }
    }
    s.append("]")
    s.toString
  }
}
