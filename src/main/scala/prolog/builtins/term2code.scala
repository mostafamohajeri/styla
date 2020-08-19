package prolog.builtins
import prolog.terms._
import prolog.fluents.Expander

import prolog.interp.Prog

// todo
final class term2code() extends FunBuiltin("term2code",2) {
 override def exec(p: Prog) = {
     val t=getArg(0)
     val e=new Expander().expandHeadTerm(t)
     println("unfinished: todo")
     putArg(1,e,p)
  }
}
