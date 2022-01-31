package prolog

import prolog.terms.{Conj, Const, Fun, Var}

//import prolog.io.IO.println

object ConcurrentMain {
  def main(args: Array[String]) {
    println("start...")
    val logicEngine = new LogicEngine()

    for(i <- 5 to 5 )  {
      logicEngine.setGoal(f"assert(complement(C,C,C))")
      logicEngine.setGoal(f"assert(color(blue))")
      logicEngine.setGoal(f"assert(nb_meetings(10))")


//      val a = new assert()
      logicEngine.askAnswer()
    }

//    val assert= new assert()
//    assert.args = Array(new Fun("m",Array(SmallInt(1003))))
//    println(assert)
//    logicEngine.setGoal(assert)
//    logicEngine.askAnswer()
//


    //    logicEngine.setGoal("assert(m(1))")
//    logicEngine.askAnswer()
//
//    logicEngine.setGoal("assert(m(2))")
//    logicEngine.askAnswer()
//
//    val g = new Fun("assert",Array(new Fun("m",Array(new SmallInt(10)))))

//    println(List[Term](g))

//    logicEngine.set_query(List[Term](g))
//    logicEngine.askAnswer()

//    logicEngine.setGoal("true :- m(X) , m(Y) , X == Y")

//    logicEngine.setGoal(())

//    val parser = new TermParser()
//
//
//    var X = new Var("X")
//
//    val d2 = Conj.build(new Fun("m",Array(X)),new Fun("\\+",Array(new Fun("n",Array(X)))))
////    logicEngine.setGoal("true:- m(B) , between(1,10,X) , C is B + X")
//
//    logicEngine.setGoal(new  Fun("between",Array(SmallInt(1),SmallInt(2),X)))
//
//
////    val a = new assert()
////    a.args=Array(new Fun("m",Array(new SmallInt(1))))
//
//
//    var more = true
//
//    while (more) {
//      val answer = logicEngine.askAnswer()
//      if (null == answer) {
//        more = false
//        println("no more")
//      } else {
//        println("subs:")
//        println(logicEngine.substitutions())
//      }
//    }
    //List(color(_2688060384),nb_meetings(_468588614),complement(_2688060384,blue,_1304399722))
    //List(color(_3339425356),nb_meetings(_1886163074),complement(_3339425356,blue,_1367925304))




    var N = new Var("N")
    var C = new Var("C")
    var C1 = new Var("C1")

//    val le2 = new LogicEngine()

    val q = Conj.build(Conj.build(new  Fun("color",Array(C)),new Fun("nb_meetings",Array(N))),new Fun("complement",Array(C,new Const("blue"),C1)))
//    new Fun("complement",Array(C,new Const("blue"),C1))
    println(q)

    logicEngine.setGoal(null,q)
//    logicEngine.setGoal("true:- color(X),nb(N), complement(X,blue,Y)")
    var more = true

    while (more) {
      val answer = logicEngine.askAnswer()

      println(logicEngine)
      if (null == answer) {
        more = false
        println("no more")
      } else {
        println("subs:")
        println(logicEngine.substitutions())
      }
    }

  }
}

