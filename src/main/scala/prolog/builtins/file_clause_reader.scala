package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

final class file_clause_reader() extends FunBuiltin("file_clause_reader", 2) {

  override def exec(p: Prog) = {
    val s = getArg(0).toString

    val f = new ClauseReader(s)
    putArg(1, f, p)
  }
}

