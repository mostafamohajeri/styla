package prolog.builtins

import prolog.terms._
import prolog.interp.Prog
import prolog.acts.ScalaLogicActor

final class scala_actor_send() extends FunBuiltin("scala_actor_send", 2) {

  override def exec(p: Prog) = {
    val a = getArg(0).asInstanceOf[ScalaLogicActor]
    val msg = getArg(1)
    a ! msg
    1
  }
}