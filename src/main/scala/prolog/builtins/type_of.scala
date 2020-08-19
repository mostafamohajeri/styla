package prolog.builtins
import prolog.terms._
import prolog.io.IO
import prolog.interp.Prog

final class type_of() extends FunBuiltin("type_of", 2) {
  override def exec(p: Prog) = {
    val t0 = getArg(0)

    val r: Const = t0 match {
      case _: Var => type_of.V
      case _: Fun => type_of.F
      case _: Const => type_of.A
      case _: SmallInt => type_of.I
      case x: Real => if (x.nval.isValidInt) type_of.I else type_of.R
      case _: SystemObject => type_of.S
    }
    putArg(1, r, p)
  }
}

object type_of {
  val V = new Const("var")
  val F = new Const("compound")
  val A = new Const("atom")
  val R = new Const("float")
  val I = new Const("integer")
  val S = new Const("system_object")
}