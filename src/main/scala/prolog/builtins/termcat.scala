package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

final class termcat() extends FunBuiltin("termcat", 3) {
  override def exec(p: Prog) = {
    val f0 = getArg(0)
    val a0 = getArg(1)

    val r = termcat.action(f0, a0)
    if (r.eq(null)) IO.errmes("bad args", this, p)
    else putArg(2, r, p)
  }
}

object termcat {
  def action(f0: Term, a0: Term): Term =
    (f0, a0) match {
      case (f: Fun, a: Fun) => {
        val t = new Fun(f.name)
        t.args = new Array(f.args.length + a.args.length)
        Array.copy(f.args, 0, t.args, 0, f.args.length)
        Array.copy(a.args, 0, t.args, f.args.length, a.args.length)
        TermParser.toFunBuiltin(t)
      }
      case (c: Const, a: Fun) => {
        val t = new Fun(c.name)
        t.args = a.args
        TermParser.toFunBuiltin(t)
      }
      case _ => null
    }
}