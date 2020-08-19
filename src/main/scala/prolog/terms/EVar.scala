package prolog.terms

final class EVar(var_name: String = "") extends Var(var_name) {
  override def tcopy(dict: Copier): Term = {
   this.ref
  }

  override def toString = {
    if (unbound) {
      val h: Long = hashCode
      val n: Long = if (h < 0) 2 * (-h) + 1 else 2 * h
      "~_" + n
    } else ref.toString
  }
   
}