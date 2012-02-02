package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class vars_of() extends FunBuiltin("vars_of", 2) {

  override def exec(p: Prog) = {
    val vs = getArg(0).vars_of
    putArg(1, vs, p)
  }
}