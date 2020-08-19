package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class abolish() extends FunBuiltin("abolish", 2) {
  override def exec(p: Prog) = {
    val f = getArg(0).asInstanceOf[Const]
    val n = getArg(1).asInstanceOf[Num].getValue.toInt
    val g = new Fun(f.sym)
    g.init(n)
    p.db.cleanUpKey(g)
    1
  }
}