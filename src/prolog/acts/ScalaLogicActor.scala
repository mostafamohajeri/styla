package prolog.acts
import scala.actors.Actor
import prolog.LogicEngine
import prolog.terms._
import prolog.fluents.DataBase

/**
 * Prolog-based Actor using Scala's built-in Actor API
 * see builtins starting with "scala_" for use from Prolog
 *
 * message handlers are simply Prolog clauses -
 *    if the head unifies the body triggers actions -
 *    typically that includes sending messages to other Actors
 */

class ScalaLogicActor(aName: String, db: DataBase)
  extends LogicActor(aName, db) with Actor {
  start

  def sendTo(msg: Term) {
    this ! msg
  }

  def getSender: Term = {
    sender match {
      case x: ScalaLogicActor => Const.the(x)
      case other => Const.no
    }
  }

  def act() {
    loop {
      react {
        case stop_msg: Const if stop_msg.name == "$stop" => {
          stopLogic
          exit()
        }
        case goal_msg: Term => logicAction(goal_msg)
        case other => otherAction(other)

      }
    }
  }
}