package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class to_string() extends FunBuiltin("to_string", 2) {

  override def exec(p: Prog) = {
    val s = getArg(0).asInstanceOf[Nonvar].toString
    putArg(1, new Const(s), p)
  }
}