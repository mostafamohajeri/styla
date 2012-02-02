package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class integer_source() extends FunBuiltin("integer_source", 5) {
  override def exec(p: Prog) = {
    val fuel = getArg(0).toString.toInt
    val a = getArg(1).toString.toInt
    val x = getArg(2).toString.toInt
    val b = getArg(3).toString.toInt
    val s = new IntegerSource(fuel, a, x, b)
    putArg(4, s, p)
  }
}