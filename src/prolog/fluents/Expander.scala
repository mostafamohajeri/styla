package prolog.fluents
import prolog.terms._
import prolog.builtins._
import scala.collection.mutable._

/**
 * currently not used
 */
class Expander {
  val map = new LinkedHashMap[Var, Var]
  val code = new ArrayBuffer[Term]()
  def expandHeadTerm(h: Term): Term = {
    h match {
      case v: Var => {
        val u: Var = map.getOrElseUpdate(v, new Var())
        code += new eq().as(u, h)
        u
      }
      case f: Fun => {
        //if(
        for (i <- 1 until f.args.length) {
          val x = f.args(i)
          val t = new arg().as(i, f, x)
        }
        code += new eq().as(f, h)
        f
      }
      case other => {
        code += new eq().as(other, h)
        other
      }

    }
  }
}
