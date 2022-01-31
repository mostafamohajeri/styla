package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

final class  halt() extends ConstBuiltin("halt") {
  override def exec(p: Prog) = {
    IO.stop
    1
  }
}