package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.io._
import prolog.interp.Prog

final class listing() extends FunBuiltin("listing", 1) {

  override def exec(p: Prog) = {
    val pred = getArg(0)
    val rvars = p.db.revVars()
    if (pred.isInstanceOf[Var]) {
      p.db.foreach(x =>
        { x._2.foreach(x => TermParser.printclause(rvars, x)); println })
      1
    } else {
        def listpred(p: Prog, pred: Term): Int = {
          val cs = p.db.getMatches(List(pred), false)
          if (cs.eq(null)) 0
          else {
            cs.foreach(x => TermParser.printclause(rvars, x))
            1
          }
        }
      val f = new Fun(pred.toString)
      var found = 0
      for (i <- 0 until 16) {
        f.args = new Array[Term](i)
        found += listpred(p, f)
      }
      if (found > 0) 1 else 0
    }
  }
}
