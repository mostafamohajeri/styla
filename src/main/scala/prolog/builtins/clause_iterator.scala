package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.io._
import prolog.interp.Prog

final class clause_iterator() extends FunBuiltin("clause_iterator", 2) {

  class ClauseIterator(i: Iterator[List[Term]]) extends Iterator[Term] {
    def hasNext = i.hasNext
    def next = Clause.fromList(i.next)
  }

  override def exec(p: Prog) = {
    val h = getArg(0)
    val i: Iterator[List[Term]] = h match {
      case _: Var => {
        val cs = for (x <- p.db.iterator) yield x._2.toList.flatten
        cs
      }
      case _: Term => {
        val cs = p.db.getMatches(List(h), false)
        if (cs.eq(null)) null
        else cs.iterator
      }
    }
    if (i.eq(null)) 0
    else {
      val s = new IteratorSource(new ClauseIterator(i))
      putArg(1, s, p)
    }
  }
}
