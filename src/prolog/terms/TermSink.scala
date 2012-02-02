package prolog.terms

trait TermSink extends Fluent {
  def putElement(T: Term): Int

  def collect: Term
}