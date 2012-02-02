package prolog.builtins
import prolog.terms._
import prolog.interp.Prog
import scala.util.Sorting

class stable_sort extends FunBuiltin("stable_sort", 3) {
  def dir(down: Boolean, x: Term, y: Term): Boolean =
    if (down) Term.tcompare(x, y) > 0
    else Term.tcompare(x, y) < 0

  override def exec(p: Prog) = {
    val how: Boolean = getArg(0).toString == ">"
    val res: List[Term] = getArg(1) match {
      case Const.nil => Nil
      case c: Cons => {
        val xs: List[Term] = Cons.toList(c)
        val ys = Sorting.stableSort[Term](xs, { (x: Term, y: Term) => dir(how, x, y) })
        ys.toList
      }
    }
    putArg(2, Cons.fromList(res), p)
  }
}
