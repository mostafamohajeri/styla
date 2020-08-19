package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class put() extends FunBuiltin("put", 2) {
  override def exec(p: Prog) = {
    val s = getArg(0).asInstanceOf[TermSink]
    s.putElement(getArg(1))
  }
}