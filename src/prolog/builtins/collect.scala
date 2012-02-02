package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class collect() extends FunBuiltin("collect", 2) {

  override def exec(p: Prog) = {
    putArg(1, getArg(0).asInstanceOf[TermSink].collect, p)
  }
}