package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final case class cut(x: Var) extends FunBuiltin("cut", 1) {
  args = new Array(1)
  args(0) = x

  override def exec(p: Prog) = {
    val orTop = getArg(0).asInstanceOf[SmallInt].nval
    var i = p.orStack.size - orTop
    while (i >= 0) {
      if (!p.orStack.isEmpty) p.orStack.pop()
      i -= 1
    }
    1
  }
}

