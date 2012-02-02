package prolog.terms

class Trail extends ObjectStack[Var] {
  def unwind(to: Int) {

    var i = size - to
    while (i > 0) {
      val V = pop()
      V.undo
      i -= 1
    }
  }
}