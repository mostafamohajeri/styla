package prolog.builtins
import prolog.terms._
import prolog.interp.Prog
import prolog.io.IO

final class arg() extends FunBuiltin("arg", 3) {
  
  def as(i: Int, f: Fun, x: Term) = {
    args = Array(SmallInt(i), f, x)
    this
  }
  
  
  override def exec(p: Prog) = {
    val f0 = getArg(1)
    val i0 = getArg(0)

    (f0, i0) match {
      case (f: Fun, i: Num) => {
        val k = i.getValue.toInt
        if (k < 0 || k > f.args.length) IO.errmes("bad args", this, p)
        else {
          val x = if (0 == k) new Const(f.name) else f.args(k - 1)
          putArg(2, x, p)
        }
      }
      case (f: Const, i: Num) => {
        val k = i.getValue.toInt
        if (0 == k) putArg(2, f, p) else {
          IO.errmes("bad args", this, p)
        }
      }
      case _ => IO.errmes("bad args", this, p)
    }
  }
}