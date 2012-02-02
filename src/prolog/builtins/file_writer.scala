package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

final class file_writer() extends FunBuiltin("file_writer", 2) {

  override def exec(p: Prog) = {
    val s = getArg(0).toString
    val f = new TermWriter(s)
    putArg(1, f, p)
  }
}

