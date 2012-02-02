package prolog.io
import prolog.terms._
import java.io._

class TermWriter(val fname: String) extends TermSink {

  val out =
    if ("stdio" == fname) scala.Console.out
    else new PrintWriter(new OutputStreamWriter(
      new FileOutputStream(fname), "UTF-8"));

  override def putElement(x: Term) = {
    out.append(x.toString)
    //out.flush()
    1
  }

  override def stop() {
    out.close()
  }

  def collect = new Const(fname)

  override def toString = "{" + getClass().getName() + ":" + fname + "}"
}