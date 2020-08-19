package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class new_engine() extends FunBuiltin("new_engine", 4) {

  override def exec(p: Prog) = {
    val files = getArg(0)
    val answer = getArg(1)
    val goal = getArg(2)
    val db = Prog.make_db(files, p)
    val q = new Prog(db)
    Prog.init_with(db, answer, goal, q)
    putArg(3, q, p)
  }
}