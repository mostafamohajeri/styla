package prolog.builtins

import prolog.terms._
import prolog.interp.Prog
import prolog.acts.LogicActor

final class actor_send() extends FunBuiltin("actor_send", 2) {

  override def exec(p: Prog) = {
    val a = getArg(0).asInstanceOf[LogicActor]
    val msg = getArg(1)
    a.sendTo(msg)
    1
  }
}