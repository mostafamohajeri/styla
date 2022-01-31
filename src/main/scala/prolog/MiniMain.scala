package prolog

import java.util.concurrent.{ArrayBlockingQueue, Executors}
import prolog.builtins.{assert, true_}
import prolog.fluents.DataBase
import prolog.io.TermParser
import prolog.terms.{Clause, Conj, Cons, Const, Copier, Fun, FunBuiltin, SmallInt, Term, Trail, Var}

import scala.concurrent.duration.Duration
import scala.collection.mutable.ListBuffer
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Random
import java.util.concurrent.ArrayBlockingQueue
//import prolog.io.IO.println

object MiniMain {

  case class adder() {
    def add(a: Int,b:Int) = a + b
  }

  def main(args: Array[String]) {
    println("start...")






    val db = new DataBase(null)


    val b = Cons.fromList(List(new Const("ff1"),new Const("ff2"),new Const("ff3")))

    println(b)


    var a : ArrayBlockingQueue[LogicEngine] = new ArrayBlockingQueue[LogicEngine](5)

    for(i <- 1 to 2) {
      a.put(new LogicEngine(db))
    }

    implicit val ec = new ExecutionContext {
      val threadPool = Executors.newFixedThreadPool(10)

      def execute(runnable: Runnable) {
        threadPool.submit(runnable)
      }

      def reportFailure(t: Throwable) {}
    }

    for(i <- 1 to 1 )  {
//      println(db.has_clauses(a.peek().parser.parse("m(3)").head))
//      println(db.has_clauses(a.peek().parser.parse("m(1)").head))
//      println(db.has_clauses(a.peek().parser.parse("m(2)").head))
//      a.peek().setGoal(f"assert(m(1))")
//      a.peek().setGoal(f"assert(m(2))")


      var D = new Var("D")

      println(db.pushIfNotExists(List(new Fun("m",Array(new SmallInt(3)),"hello"))))
      println(db.pushIfNotExists(List(new Fun("m",Array(new SmallInt(3))))))
      println(db.pushIfNotExists(List(new Fun("m",Array(new SmallInt(1))))))
      println(db.pushIfNotExists(List(new Fun("m",Array(new SmallInt(2))))))
//      println(db.pushIfNotExists(List(new Fun("m",Array(new SmallInt(4))))))
      println(db.pushIfNotExists(List(new Fun("n",Array(new SmallInt(3))))))
      println(db.pushIfNotExists(List(new Fun("n",Array(new SmallInt(1))))))
      println(db.pushIfNotExists(List(new Fun("n",Array(new SmallInt(2))))))
      println(db.pushIfNotExists(List(new Fun("n",Array(new SmallInt(4))))))
      println(db.pushIfNotExists(List(new Fun("d",Array(D)),new Conj(new Fun("m",Array(D)),new Fun("m",Array(D))))))

      println(db.pushIfNotExists(List(new Fun("a",Array(new SmallInt(1))))))
      println(db.push(a.peek().parser.parse("c(A,B) :- m(A),n(B),A=B")))
      println(db.push(a.peek().parser.parse("c(A,B) :- m(A),x(B),A=B")))

      db.push(a.peek().parser.parse("most_prefered(Pred) :- copy_term(Pred,Pred2), Pred, forall(Pred2,(dominates(Pred2,Pred)->false ;true))."))
      println(db.push(a.peek().parser.parse("main(fish).")))

      println(db.push(a.peek().parser.parse("x(chello).")))
      println(db.push(a.peek().parser.parse("x(hello).")))

      println(db.push(a.peek().parser.parse("dominates(x(A),_) :- A == hello.")))
      println(db.push(a.peek().parser.parse("dominates(T , T) :- !,false.")))
      println(db.push(a.peek().parser.parse("x(dello).")))
      db.push(a.peek().parser.parse("good(X) :-   ! , n(D) ."))
      println(db.push(a.peek().parser.parse("x(kello).")))


//      println(db.delIfExists(a.peek().parser.parse("m(3)").head))
//      println(db.delIfExists(a.peek().parser.parse("m(3)").head))


//      logicEngine3.setGoal(f"assert(m(3))")

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

//    val q = Conj.build(Conj.build(new  Fun("color",Array(C)),new Fun("nb_meetings",Array(N))),new Fun("complement",Array(C,new Const("blue"),C1)))
//    val q = Conj.build(Conj.build(new  Fun("color",Array(C)),new Fun("nb_meetings",Array(N))),new Fun("complement",Array(C,new Const("blue"),C1)))
//    new Fun("complement",Array(C,new Const("blue"),C1))
//    println(q)

    val G = new Var("G")
//    val S = new Var("S")

    var T1 = new Fun("h",Array(G))
    var T2 = new Fun("h",Array( new Const("ff")))

    println(T1)
    println(T2)

    println("unifies:"+(T1.unify(T2,new Trail())))

    println(T1)


    val tasks =
      for (i <- 1 to 1) yield Future {
        val engine = a.take()
        val N = new Var("N")
        val S = new Var("S")
        val is = TermParser.toFunBuiltin(new Fun("xxx",Array(N,S)))
        println(is)
//        if(i % 2  == 0)
//          engine.setGoal(f"assert(m($i))")
//        else
//          engine.set_query(List(new Fun("a",Array(N)), new FunBuiltin("is",Array(S,N))))
          engine.setGoal("good(M).")

        //    logicEngine1.setGoal("true:- color(X),nb(N), complement(X,blue,Y)")
        var more = true

        while (more) {
          val answer = engine.askAnswer()

          if (null == answer) {
            more = false
            println(f"engine: no more")

          } else {

            println(f"${engine.query }: subs: ${engine.substitutions()}")
            println("has Answer")
          }
        }
        a.put(engine)
      }

    val aggr = Future.sequence(tasks)
    Await.result(aggr, Duration.Inf)

//    for(i <- 1 to 5 ) {
//
//      val chosenEngine = new Random().nextInt(engines.size)
//      val engine = engines( chosenEngine )
//      val N = new Var("N")
//      engine.setGoal(new  Fun("m",Array(N)))
//      //    logicEngine1.setGoal("true:- color(X),nb(N), complement(X,blue,Y)")
//      var more = true
//
//      while (more) {
//        val answer = engine.askAnswer()
//
//        if (null == answer) {
//          more = false
//          println(f"engine $chosenEngine: no more")
//
//        } else {
//          println(f"engine $chosenEngine: subs:")
//          println(engine.substitutions())
//        }
//      }
//    }
  }
}

