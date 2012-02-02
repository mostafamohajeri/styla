package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.io._
import prolog.interp.Prog

final class traceln() extends FunBuiltin("traceln", 1) {

  override def exec(p: Prog) = {
    val x = getArg(0)
    IO.println(x.toString)
    1
  }
}
