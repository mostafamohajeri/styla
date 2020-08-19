package prolog.builtins
import prolog.terms._
import prolog.io.IO
import prolog.interp.Prog

final class char2code() extends FunBuiltin("char2code", 2) {
  override def exec(p: Prog) = {
    val s: String = getArg(0).asInstanceOf[Const].toString
    if (s.length != 1) IO.errmes("bad args in", this, p)
    else {
      val c = s.charAt(0)

      putArg(1, new SmallInt(c), p)
    }
  }
}