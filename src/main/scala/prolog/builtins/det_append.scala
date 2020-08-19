package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

class det_append extends FunBuiltin("det_append", 3) {
  def cons(x: Term, xs: Term) =
    if (null == xs) null else new Cons(x, xs)

  def app(xs: Term, ys: Term): Term = {
    xs match {
      case c: Cons => cons(c.getHead, app(c.getBody, ys))
      case Const.nil => ys
      case other: Term => null
    }
  }

  override def exec(p: Prog) = {

    val res = app(getArg(0), getArg(1))

    if (null == res) 0
    else putArg(2, res, p)
  }

}
