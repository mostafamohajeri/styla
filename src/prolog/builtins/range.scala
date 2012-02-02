package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class range() extends FunBuiltin("range", 3) {
  override def exec(p: Prog) = {
    val x = getArg(0).toString.toInt
    val y = getArg(1).toString.toInt
    val xs = new Range(x, y, 1).map { x => SmallInt(x) }
    val ns = xs.foldRight[Term](Const.nil) { (x, ys) => new Cons(x, ys) }

    putArg(2, ns, p)
  }
}