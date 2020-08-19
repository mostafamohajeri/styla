package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog
final class string_sink() extends FunBuiltin("string_sink", 1) {
  override def exec(p: Prog) = {
    putArg(0, new StringSink(), p)
  }
}