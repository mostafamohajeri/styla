package prolog.builtins

import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog
import prolog.acts.ScalaLogicActor

final class scala_actor_new() extends FunBuiltin("scala_actor_new", 2) {

  override def exec(p: Prog) = {
    val files = getArg(0)
    val db = Prog.make_db(files, p)
    val q = new ScalaLogicActor(files.toString, db)
    putArg(1, q, p)
  }
}