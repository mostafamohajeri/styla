package prolog.io
//import jdk.internal.jline.console.ConsoleReader
import prolog.terms._
import prolog.interp.Prog
//import tools.jline.console.ConsoleReader
import java.io.File

object IO {

  def time = System.currentTimeMillis()

  val t0 = time

  def start = {
    printlnInfo("Styla: Scala Terms Yield Logic Agents")
    printlnInfo("Prolog System Version 1.3.4 with LogicActors")
    printlnInfo("Copyright (C) Paul Tarau 2011-2014")
  }

  def stop = {
    printlnInfo("Prolog execution halted")
    printlnInfo("total time in ms=" + (time - t0))
    System.exit(0)
  }

  final def find_file(f0: String): String = {
//    if ("stdio" == f0) return f0
//    val fs1 = List(f0 + ".pro", f0 + ".pl", f0)
//    val fs2 = fs1.map(x => "progs/" + x)
//    val fs = (fs1 ++ fs2).dropWhile(x => !new File(x).exists())
//    if (fs.isEmpty) {
//      IO.warnmes("file not found: " + f0)
//      null
//    } else
//      fs.head
    throw new RuntimeException("not imp")
  }

//  val stdio = new ConsoleReader()

  def readLine(prompt: String): String = {
//    IO.stdio.readLine(prompt)
    throw new RuntimeException("not imp")
  }


  def printlnInfo(s: Any) {
    println(s.toString)
    //Console.flush()
  }

  def print(s: Any) {
    print(s.toString)
  }

  def errmes(mes: String, culprit: Term, p: Prog) = {
    printlnInfo("*** " + mes + " in => " + culprit)
    p.stop()
    0
  }

  def warnmes(mes: String) = {
    printlnInfo("*** " + mes)
    0
  }

}

