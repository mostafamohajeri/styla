package prolog.acts

import prolog.LogicEngine
import prolog.terms._
import prolog.fluents.DataBase

import akka.actor._

class AkkaLogicActor(aName: String, db: DataBase, aref: ActorRef)
  extends LogicActor(aName, db) {

  var lastSender: ActorRef = null

  val skinref = makeSkinRef(aref)

  def makeSkinRef(x: Any) = x match {
    case x if null == x => AkkaLogicActor.create(this, aName)
  }

  def sendTo(msg: Term) {
    skinref ! SEND(msg)
  }

  def getSender: Term =
    if (null == lastSender) Const.no
    else Const.the(new AkkaLogicActorWrapper(lastSender))
}

class AkkaLogicActorWrapper(aref: ActorRef)
  extends AkkaLogicActor(aref.path.toString, null, aref) {

  override def makeSkinRef(x: Any) = x match {
    case aref: ActorRef => aref
  }
}

sealed trait MessageTerm
case class SEND(msg: Term) extends MessageTerm

class ActorSkin(val actor: AkkaLogicActor) extends Actor {

  override def preStart() {
    //println("prestart=" + actor)
  }

  override def postStop() = {
    actor.stopLogic
    //println("logic stopped for=" + actor)
  }

  def setSenderOf(msg: Any, sender: ActorRef) {
    //println("sender=" + sender)
    actor.lastSender = sender
  }

  def receive = {
    case stop_msg: Const if stop_msg.name == "$stop" => {
      setSenderOf(stop_msg, sender)
      context.stop(self)
    }
    case goal_msg: Term => {
      setSenderOf(goal_msg, sender)
      actor.logicAction(goal_msg)
    }
    case SEND(msg) => self ! msg
    case other => {
      setSenderOf(other, sender)
      actor.otherAction(other)
    }

  }
}

object AkkaLogicActor {
  def stop = asystem.shutdown()
  val asystem = ActorSystem.create()

  /*
  val topActorRef =
    asystem.actorOf(Props[ActorSkin], name = "topAkkaLogicActor")
  */

  def create(logicActor: AkkaLogicActor, aName: String) = {
    asystem.actorOf(Props(new ActorSkin(logicActor)), name = aName)
  }

}

