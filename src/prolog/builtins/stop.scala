package prolog.builtins
import prolog.terms._
import prolog.interp.Prog
final class stop() extends FunBuiltin("stop", 1) {
  override def exec(p: Prog) = {
    val s = getArg(0).asInstanceOf[Fluent]
    s.stop()
    1
  }
}