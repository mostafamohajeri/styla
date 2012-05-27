package prolog.builtins

import prolog.terms._
import prolog.interp.Prog
import prolog.acts.LogicActor

final class actor_send() extends FunBuiltin("actor_send", 2) {

  override def exec(p: Prog) = {
    val x = getArg(0)
    val msg = getArg(1)
    x match {
      case a: LogicActor => a.sendTo(msg)
    }
    1
  }
}