package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

class term_compare extends FunBuiltin("term_compare", 3) {

  override def exec(p: Prog) = {
    putArg(2, SmallInt(Term.tcompare(getArg(0), getArg(1))), p)
  }
}
