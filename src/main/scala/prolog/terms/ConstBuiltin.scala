package prolog.terms
import prolog.interp.Prog

abstract class ConstBuiltin(sym: String) extends Const(sym) {
  override def exec(p: Prog): Int
}