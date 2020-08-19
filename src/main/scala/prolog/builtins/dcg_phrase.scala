package prolog.builtins
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

class dcg_phrase extends FunBuiltin("dcg_phrase", 4) {

  override def exec(p: Prog) = {

    val h = getArg(0)
    val b = getArg(1)
    val bs = TermParser.DCG_MARKER :: Conj.toList(b)
    val cs = TermParser.postProcessBody(h, bs)
    if (null == cs) 0
    else {
      putArg(2, cs.head, p)
      putArg(3, Conj.fromList(cs.tail), p)
    }
  }

}
