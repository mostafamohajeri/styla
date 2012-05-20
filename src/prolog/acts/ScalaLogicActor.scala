package prolog.acts
import scala.actors._
import prolog.LogicEngine
import prolog.terms._
import prolog.fluents.DataBase

class ScalaLogicActor(aName: String, db: DataBase)
  extends SystemObject with Actor {
  override def name = aName
  val engine = new LogicEngine(db)
  //engine.set

  private def call(msg: Term): Term = {
    engine.setGoal(new Var(), msg)
    //println("Answer:-GOAL=" + answer + ":-" + goal)
    engine.askAnswer()
  }

  def act() {
    loop {
      react {
        case msg: String if msg == "$stop" => {
          engine.stop // destroy Engine
          exit()
        }
        case msg: Term => {
          //println("sender=" + sender)
          val from = sender match {
            case x: ScalaLogicActor => Const.the(x)
            case other => Const.no
          }
          val guard = new Fun("set_last_sender", Array(from))
          val g = new Conj(guard, msg)
          //println("GOT:" + g)
          val result = call(g)
          //println("res=" + result)
        }
      }
    }
  }
}