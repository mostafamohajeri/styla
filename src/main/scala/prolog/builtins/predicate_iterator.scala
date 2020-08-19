package prolog.builtins
import prolog.terms._
import prolog.fluents._
import prolog.interp.Prog

final class predicate_iterator extends FunBuiltin("predicate_iterator", 1) {

  override def exec(p: Prog) = {
    val i = for (x <- p.db.iterator) yield DataBase.key2fun(x._1)

    if (i.eq(null)) 0
    else {
      val s = new IteratorSource(i)
      putArg(0, s, p)
    }
  }
}