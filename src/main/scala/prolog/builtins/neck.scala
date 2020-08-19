package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final case class neck(x: Var) extends FunBuiltin("neck", 1) {
  args = new Array(1)
  args(0) = x

  override def exec(p: Prog) = {
    var k = p.orStack.size
    //println("popped=" + p.popped + ":" + k + ":neck !!! k++\n" + p.orStack)
    if (p.popped)
      k += 1
    putArg(0, SmallInt(k), p)
  }
}
