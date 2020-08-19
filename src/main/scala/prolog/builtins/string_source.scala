package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class string_source() extends FunBuiltin("string_source", 2) {
  override def exec(p: Prog) = {
    putArg(1, new IteratorSource(getArg(0).asInstanceOf[Const].toString.iterator), p)
  }
}