package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class ctime() extends FunBuiltin("ctime", 1) {

  val t0 = System.currentTimeMillis()

  override def exec(p: Prog) = {
    val T: Real = Real(System.currentTimeMillis() - t0)
    putArg(0, T, p)
  }
}