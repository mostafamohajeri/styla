package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class code2char() extends FunBuiltin("code2char", 2) {
  override def exec(p: Prog) = {
    val c: Char = getArg(0).asInstanceOf[Num].getValue.toInt.asInstanceOf[Char]
    val s = c.toString
    putArg(1, new Const(s), p)
  }
}