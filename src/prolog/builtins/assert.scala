package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class assert() extends FunBuiltin("assert", 1) {
  override def exec(p: Prog) = {
    val c = Clause.toClauseList(getArg(0).copy)
    p.db.add(c)
    1
  }
}