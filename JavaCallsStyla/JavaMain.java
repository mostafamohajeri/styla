import prolog.LogicEngine;
import prolog.terms.*;

class JavaMain {
  public static void main(String[] args) {
    /*
    // a static variant - if you know you only need one engine
    public static defaultLogicEngine=null
    public static void initProlog() = {
      defaultLogicEngine=new LogicEngine();
    }
    */
    
    LogicEngine logicEngine = new LogicEngine();
    
    logicEngine.setGoal("consult('simple/b')");
    //logicEngine.askAnswer();
    
    logicEngine.setGoal("b(X)");

    boolean more = true;
    while (more) {
      Object answer = logicEngine.askAnswer();
      if (null == answer)
        more = false;
      else
        System.out.println("answer=" + answer);
    }
    
    logicEngine.setGoal("halt");
    logicEngine.askAnswer();
    
  }
}
