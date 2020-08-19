package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class copy_term() extends FunBuiltin("copy_term", 2) {
  override def exec(p: Prog) = {
    putArg(1, getArg(0).copy, p)
  }
}