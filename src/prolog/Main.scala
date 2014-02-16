package prolog
import prolog.interp.Prog
import prolog.io.IO
import prolog.io.TermParser
import prolog.terms.Term

object Main extends App {
  go

  def go = {
    IO.start
    toplevel(args.toList)
    IO.stop // println("\nProlog execution halted\n")
  }

  def toplevel(topgoals: List[String]) {

    val prog: Prog = new Prog()
    val parser = new TermParser() //prog.db.vars)
      def printvar(x: (String, Term)) = {
        val a = x._1.toString
        //val b: String = TermParser.term2string(prog.db.revVars(), List(x._2), "")
        val b: String = TermParser.term2string(x._2)
        if (a != b && !a.startsWith("_"))
          IO.println(a + " = " + b)
      }

    topgoals.foreach { x =>
      parser.vars.clear()
      val gv = parser.parse(x)
      if (null != gv) {
        prog.set_query(gv)
        var more = true
        while (more) {
          val answer = prog.getElement()
          if (answer.eq(null)) more = false
          else
            parser.vars.foreach(printvar)
        }
      }
    }

    var goalWithVars = (parser.parse("true. "), parser.vars)

    while (!goalWithVars.eq(null)) {
      goalWithVars = parser.readGoal()

      if (!goalWithVars.eq(null)) {
        val goal = goalWithVars._1
        val vars = goalWithVars._2
        prog.set_query(goal)
        var more = true
        while (more) {
          val answer = prog.getElement()
          if (answer.eq(null)) more = false
          else {
            if (vars.isEmpty) IO.println("yes.")
            else {
              vars.foreach(printvar)
              IO.println(";")
            }
          }
        }
        IO.println("no (more) answers\n")
        prog.trail.unwind(0)
      }
     
    }
  }
}
