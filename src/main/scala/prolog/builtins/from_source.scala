package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class from_source() extends FunBuiltin("from_source", 2) {
  override def exec(p: Prog) = {
    val r = getArg(0).asInstanceOf[TermSource]
    putArg(1, r.getElement(), p)
  }
}
