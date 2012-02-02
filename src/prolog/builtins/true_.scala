package prolog.builtins
import prolog.terms._
import prolog.interp.Prog

final case class true_() extends ConstBuiltin("true") {
  override def exec(p: Prog) = 1
}