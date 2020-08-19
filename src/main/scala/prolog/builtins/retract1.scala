package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class retract1() extends FunBuiltin("retract1", 1) {
  override def exec(p: Prog) = {
    val g = getArg(0)
    val cs = p.db.del1(g)
    if (cs.eq(null)) 0
    else putArg(0, cs.head, p)
  }
}