package prolog.builtins
import prolog.terms._
import prolog.interp.Prog
import prolog.fluents._

final class source_to_list() extends FunBuiltin("source_to_list", 3) {
  override def exec(p: Prog) = {
    val toCopy = Const.no != getArg(0)
    val s = getArg(1).asInstanceOf[TermSource]
    putArg(2, s.toList(toCopy), p)
  }
}
