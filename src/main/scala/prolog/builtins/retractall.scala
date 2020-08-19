package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class retractall() extends FunBuiltin("retract1", 1) {
  override def exec(p: Prog) = {
    val g = getArg(0)
    val cs = p.db.delAll(g)
    1
  }
}