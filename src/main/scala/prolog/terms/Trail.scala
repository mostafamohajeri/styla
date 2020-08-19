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


  def substitutions() =
    this.filter(v => v.isInstanceOf[Var] && !v.asInstanceOf[Var].v_name.equals(""))
      .map(v => v.v_name -> v.ref)
      .toMap
}