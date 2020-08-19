package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.fluents._
import prolog.interp.Prog

final class consult() extends FunBuiltin("consult", 1) {

  override def exec(p: Prog) = {
    val x = getArg(0)
    x match {
      case c: Const => {
        val fname = c.sym
        p.db.fromFile(fname, false)
      }
      case _ => 0
    }
  }
}
