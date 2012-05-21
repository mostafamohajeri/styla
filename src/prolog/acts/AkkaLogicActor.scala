package prolog.acts

import prolog.LogicEngine
import prolog.terms._
import prolog.fluents.DataBase

import akka.actor._

class AkkaLogicActor(aName: String, db: DataBase)
  extends LogicActor(aName, db) with Actor {

  def sendTo(msg: Term) {
    self ! msg
  }

  def getSender: Term = {
    context.sender match {
      case x: AkkaLogicActor => Const.the(x)
      case other => Const.no
    }
  }

  def receive = {
    case stop_msg: Const if stop_msg.name == "$stop" => {
      stopLogic
      sys.exit()
    }
    case goal_msg: Term => logicAction(goal_msg)
    case other => otherAction(other)

  }
}

