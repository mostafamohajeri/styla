package prolog.acts

import prolog.LogicEngine
import prolog.terms._
import prolog.fluents.DataBase

abstract class LogicActor(aName: String, db: DataBase)
  extends SystemObject {
  override def name = aName
  private val engine = new LogicEngine(db)

  // abstract methods

  def sendTo(msg: Term)

  def getSender: Term

  // methods used by subclasses

  def stopLogic = {
    engine.stop
  }

  private def call(msg: Term): Term = {
    val from = getSender
    val guard = new Fun("set_last_sender", Array(from))
    val g = new Conj(guard, msg)
    engine.setGoal(new Var(), g)
    //println("Answer:-GOAL=" + answer + ":-" + goal)
    engine.askAnswer()
  }

  def logicAction(goal_msg: Term) = {
    call(goal_msg)
  }

  def otherAction(other: Any) = {
    val goal_msg = new Fun("react_to", Array(new Wrapper(other)))
    call(goal_msg)
  }

}