package prolog.interp

import prolog.terms._

class Unfolder(prog: Prog, val goal: List[Term], atClause: Iterator[List[Term]])
  extends SystemObject {
  def this(prog: Prog) = this(prog, null, Nil.iterator)
  type CLAUSE = List[Term]
  type GOAL = List[Term]

  private val oldtop = prog.trail.size
  def lastClause = !atClause.hasNext

  private final def unfoldWith(cs: CLAUSE, trail: Trail): GOAL = {
    trail.unwind(oldtop)

    goal match {
      case Nil => Nil
      case g0 :: xs =>
        if (cs.head.matches(g0, trail)) {
          prog.copier.clear
          val ds = Term.copyList(cs, prog.copier)
          ds.head.unify(g0, trail)
          ds.tail ++ xs
        } else
          null
    }
    // Nil: no more work
    // null: we have failed
  }

  def nextGoal(): GOAL = {
    var newgoal: GOAL = null
    while (newgoal.eq(null) && atClause.hasNext) {
      val clause: CLAUSE = atClause.next
      newgoal = unfoldWith(clause, prog.trail)
    }
    newgoal
  }

  override def toString = "Step:" + goal + ",last=" + lastClause
}

