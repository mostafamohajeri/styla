package prolog.terms

class SystemObject extends Nonvar {
  override def name = "{" + getClass().getName() + "}"
}
