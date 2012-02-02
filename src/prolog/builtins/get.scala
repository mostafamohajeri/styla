package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class get() extends FunBuiltin("get", 2) {
  override def exec(p: Prog) = {
    val s = getArg(0).asInstanceOf[TermSource]
    val x = s.getElement()
    val r = if (x.eq(null)) x else x.copy
    putArg(1, Const.the(x), p)
  }
}