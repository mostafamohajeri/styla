package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final case class fail_() extends ConstBuiltin("fail") {
  override def exec(p: Prog) = 0
}