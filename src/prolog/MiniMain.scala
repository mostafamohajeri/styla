package prolog

object MiniMain extends App {

  val logicEngine = new LogicEngine()
  logicEngine.setGoal("member(X,[1,2,3])")

  var more = true
  while (more) {
    val answer = logicEngine.askAnswer()
    if (null == answer)
      more = false
    else
      println("answer=" + answer)
  }
}

