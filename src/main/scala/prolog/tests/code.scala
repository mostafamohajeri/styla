package prolog.tests
import prolog._
import prolog.terms._
import prolog.io._
import prolog.interp.Prog

/**
 * sketch of a compilation mechanism
 */

trait Instructions {
  def fun(name: String, arity: Int, v: Term,p:Prog): Boolean = {
    val ok = v.ref match {
      case x: Var => {
        val f = new Fun(name)
        f.init(arity)
        x.bind_to(f, p.trail)
      }
      case x: Fun => x.len == arity && name == x.name
      case _ => false
    }
    ok
  }

   def cons(c: Term,x:Term,xs:Term,p:Prog): Boolean = {
    c.ref match {
      case v: Var => {
        val f = new Cons(x,xs)
        if(v.bind_to(f, p.trail)) true
        else false
      }
      case f: Fun if f.len == 2 && "." == f.name => {
        f.args(0).unify(x,p.trail) && f.args(1).unify(xs,p.trail)
      }
      case _ => false
    }
  }

   
  def arg(i: Int, v: Term, a: Term,p:Prog): Boolean = {
    //println(i + ":" + v + ":" + a)
    val ok = v.ref match {
      case x: Fun => {
        //println("good:" + x.args(i) + "=" + a)
        val r = a.ref.unify(x.args(i), p.trail)
        //Console.println("r=" + r)
        r
      }
      case other => {
        //println("bad" + other)
        false
      }
    }
    ok
  }

  def const(name: String, a: Term, p:Prog): Boolean = {
    a.ref match {
      case c: Const => name == c.name
      case x: Var => x.bind_to(new Const(name), p.trail)
      case _ => false
    }
  }
}



object prog extends Prog 

case class append0() extends FunBuiltin("app",3) {
  
    override def exec(p:Prog) : Int = {
      val oldtop=p.trail.size
      val ok=
      args(0).unify(Const.nil,p.trail) && 
      args(2).unify(args(1),p.trail)      
      if(!ok) {   
       p.trail.unwind(oldtop)
       0
      }
      else 1
   }
 
}


case class append1() extends FunBuiltin("app",3)  with Instructions {
  
    override def exec(p:Prog) : Int = {
      val oldtop=p.trail.size
      val x=new Var()
      val xs=new Var()
      val zs=new Var()
      val xxs = args(0)
      val ys=args(1)
      val xzs = args(2)
      val ok = cons(xxs,x,xs,p) &&      
               cons(xzs,x,zs,p)
      if(!ok) { 
       p.trail.unwind(oldtop)
       0
      }
      else {
        // push call to append
        val bs=Array[Term](xs,ys,zs)
        val b= new Fun("appendx",bs)
        //println("body="+b)
        p.pushUnfolder(List(b))
        //println("orStack="+p.orStack)
        1
      }
   }
}


// from cmd line one can do: 
//       'prolog.tests.append0'([],[1,2],R).
//       'prolog.tests.append1'([1,2,3],[4,5],R).
//
//       appendx([1,2,3],[4,5],R).
// append can now be seen as a call to append0 or append1
object Test extends App {
  val p=prog
  val x=`append0`()
  x.args=Array(Const.nil,new Var(),new Cons(Const.nil,Const.nil))
  x match {
    case `append0`() if x.exec(p)>0 => {
      println("p1 ok="+x)
    }
    case _ =>println("p1 not matched="+x)
  }
}
