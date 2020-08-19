package prolog.builtins
import prolog.terms._
import prolog.interp.Prog
import prolog.io.IO

final class arity() extends FunBuiltin("arity", 2) {
  override def exec(p: Prog) = {
    val t0 = getArg(0)

    t0 match {
      case f: Fun => {
        val k = f.args.length
        putArg(1, SmallInt(k), p)
      }
      case f: Const => putArg(1, SmallInt(0), p)
      case _ => IO.errmes("bad args", this, p)
    }
  }
}