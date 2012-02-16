package prolog
import prolog.interp.Prog
import prolog.io.IO
import prolog.io.TermParser
import prolog.terms.Term

object MiniMain extends App {
  val (prog, parser) = initProlog()
  askProlog("member(X,[1,2,3])")

  def initProlog() = {
    val prog: Prog = new Prog()
    val parser = new TermParser()
    parser.vars.clear()
    (prog, parser)
  }

  def askProlog(query: String) = {
    val goal = parser.parse(query)
    println("parsed=" + goal)
    if (null != goal) {
      prog.set_query(goal)
      var more = true
      while (more) {
        val answer = prog.getElement()
        if (answer.eq(null)) more = false
        else
          println("answer=" + answer)
      }
    }
  }
}
