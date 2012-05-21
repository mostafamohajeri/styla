package prolog.builtins

import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog
import prolog.acts.AkkaLogicActor

final class actor_akka_new()
  extends FunBuiltin("actor_akka_new", 2) {

  override def exec(p: Prog) = {
    val files = getArg(0)
    val db = Prog.make_db(files, p)
    val q = new AkkaLogicActor(files.toString, db)
    putArg(1, q, p)
  }
}