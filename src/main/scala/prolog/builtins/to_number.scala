package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class to_number() extends FunBuiltin("to_number", 2) {

  override def exec(p: Prog) = {
    val s = getArg(0).toString
    putArg(1, new Real(s), p)
  }
}