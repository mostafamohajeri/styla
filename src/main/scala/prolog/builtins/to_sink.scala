package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class to_sink() extends FunBuiltin("to_sink", 2) {
  override def exec(p: Prog) = {
    val s = getArg(0).asInstanceOf[TermSink]
    s.putElement(getArg(1))
  }
}
