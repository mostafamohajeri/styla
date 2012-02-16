package prolog
import prolog.interp.Prog
import prolog.io.IO
import prolog.io.TermParser
import prolog.terms.Term

class LogicEngine extends Prog {
  val parser = new TermParser()
  parser.vars.clear()

  def setGoal(query: String) =
    set_query(parser.parse(query))

  def setGoal(answer: Term, goal: List[Term]) =
    set_query(answer, goal)

  def askAnswer() = this.getElement()
}
