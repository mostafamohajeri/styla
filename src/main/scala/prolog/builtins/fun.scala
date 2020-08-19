package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

final class fun() extends FunBuiltin("fun", 3) {
  override def exec(p: Prog) = {
    val f0 = getArg(0)
    val i0 = getArg(1)

    (f0, i0) match {
      case (f: Const, i: Num) => {
        val k = i.getValue.toInt
        if (k < 0 || f.len != 0) IO.errmes("bad args", this, p)
        else if (0 == k)
          putArg(2, TermParser.toConstBuiltin(f), p)
        else {
          val t = new Fun(f.name)
          t.init(k)
          putArg(2, TermParser.toFunBuiltin(t), p)
        }
      }
      case _ => IO.errmes("bad args", this, p)
    }
  }
}