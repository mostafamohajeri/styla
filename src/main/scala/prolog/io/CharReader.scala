package prolog.io
import prolog.terms._

class CharReader(val fname: String) extends TermSource {

  val in =
    if ("stdio" == fname) scala.io.Source.stdin else
      scala.io.Source.fromFile(fname, "utf-8") //.iterator

  override def getElement(): Term =
    if (in.hasNext) new Const(in.next.toString) else null

  override def stop() {}

  override def toString = "{" + getClass() + ":" + fname + "}"
}