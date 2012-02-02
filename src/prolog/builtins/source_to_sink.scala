package prolog.builtins
import prolog.terms._
import prolog.interp.Prog
import prolog.fluents._

final class source_to_sink() extends FunBuiltin("source_to_sink", 3) {
  override def exec(p: Prog) = {
    val from = getArg(0).asInstanceOf[TermSource]
    val to = getArg(0).asInstanceOf[TermSink]
    from.toSink(to)
    1
  }
}
