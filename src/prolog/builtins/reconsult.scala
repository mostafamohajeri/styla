package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.fluents._
import prolog.interp.Prog

final class reconsult() extends FunBuiltin("reconsult", 1) {

  override def exec(p: Prog) = {
    val x = getArg(0)
    IO.println("% consulting " + x)
    x match {
      case c: Const => {
        val fname = c.sym
        val ok = p.db.fromFile(fname, true)
        if (ok > 0) IO.println("% consulted " + x)
        ok
      }
      case _ => 0
    }
  }
}
