package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class asserta() extends FunBuiltin("asserta", 1) {
  override def exec(p: Prog) = {
    val c = Clause.toClauseList(getArg(0).copy)
    p.db.push(c)
    1
  }
}