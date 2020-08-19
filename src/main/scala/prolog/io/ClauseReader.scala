package prolog.io
import prolog.terms._

class ClauseReader(val fname: String) extends TermSource {

  val parser = new TermParser()

  val clauses = parser.file2clauses(IO.find_file(fname)).iterator

  override def getElement(): Term =
    if (clauses.hasNext) Clause.fromList(clauses.next) else null

  override def stop() {}

  override def toString = "{" + getClass().getName() + ":" + fname + "}"
}