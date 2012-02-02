package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class is_builtin() extends FunBuiltin("is_builtin", 1) {

  override def exec(p: Prog) = {
    val x = getArg(0)

    x match {
      case _: ConstBuiltin => 1
      case _: FunBuiltin => 1
      case _ => 0
    }
  }
}