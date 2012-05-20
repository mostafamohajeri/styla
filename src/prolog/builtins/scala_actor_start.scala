package prolog.builtins

import prolog.terms._
import prolog.interp.Prog
import prolog.acts.ScalaLogicActor

final class scala_actor_start() extends FunBuiltin("scala_actor_start", 1) {

  override def exec(p: Prog) = {
    val a = getArg(0).asInstanceOf[ScalaLogicActor]
    //println("a=" + a)
    a.start
    1
  }
}