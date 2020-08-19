package prolog.tests
import prolog.io.TermParser
import prolog.terms._

object Tests {

  def main(args: Array[String]): Unit = {

    println("ok")
    val X = new Var("X")
    val Y = new Var("Y")
    val A = new Var("A")
    val B = new Var("B")

    val T1 = new Fun("f", Array(X, new Const("a"), X))
    val T2 = new Fun("f", Array(new Const("b"), Y, A))
    val T3 = new Fun("f", Array(new Const("b"), Y, new Const("c")))

    println("T1=" + T1)
    println("T2=" + T2)

    val tr = new Trail()
    T1.unify(T2, tr)

    println("unified=" + T1)

    tr.foreach(x => println(x.asInstanceOf[Var].v_name + " => " + x.ref))

    println(tr)

    tr.unwind(0);

    println("unwind=" + T1)
    println("unified=" + T2)

    println(T1.unify(T3, tr))

    println(tr)

    tr.unwind(0)

    println(T1)

    val CT = T1.copy

    println("??? orig=" + T1)
    println("??? copy=" + CT)
  }

  def gutest {
    val T1 = new Fun("a", Array(new Real("1")))
    val T2 = new Fun("a", Array(new Real("2")))
    println(T1.unify(T2, new Trail()))
  }

  def btest() {
    println(TermParser.string2ConstBuiltin("true__"))
  }

}

/*
class lib extends Prog 
  def app(Xs: Term, Ys: Term, Zs: Term) = Xs.ref match = {
  case Const("[]") => {
    if(Zs.unify(Ts,Zs,trail)) 1
    
  }
}

*/

