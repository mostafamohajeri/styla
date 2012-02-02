package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class new_engine() extends FunBuiltin("new_engine", 4) {

  override def exec(p: Prog) = {
    val files = getArg(0)
    val db = if (Const.nil == files) p.db
    else {
      val file: Term = files.asInstanceOf[Cons].getHead
      val fname: String = file.asInstanceOf[Const].sym
      new DataBase(fname)
    }
    val q = new Prog(db)
    val gs0 = getArg(1) :: Conj.toList(getArg(2))
    val gs = Term.copyList(gs0, new Copier())
    q.set_query(gs.head, gs.tail)
    putArg(3, q, p)
  }
}