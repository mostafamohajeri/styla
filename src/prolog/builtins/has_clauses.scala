package prolog.builtins
import prolog.terms._
import prolog.interp.Prog
import prolog.fluents._

final class has_clauses() extends FunBuiltin("has_clauses", 2) {

  override def exec(p: Prog) = {
    putArg(1, SmallInt(p.db.has_clauses(getArg(0))), p)
  }
}
