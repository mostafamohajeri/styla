package prolog.terms

import prolog.interp.Prog

abstract class FunBuiltin(sym: String, val arity: Int) extends Fun(sym) {
  def exec(p: Prog): Int
}