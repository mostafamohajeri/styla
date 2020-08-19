package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final class sleep_ms() extends FunBuiltin("sleep_ms", 1) {
  override def exec(p: Prog) = {
    val ms = getArg(0) match {
      case i: Num => i.getValue.toInt
    }
    Thread.sleep(ms)
    1
  }
}