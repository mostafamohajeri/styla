package prolog.builtins
import prolog.terms._
import prolog.io.IO
import prolog.interp.Prog

import scala.util.Random

final class is() extends FunBuiltin("is", 2) {
  def eval(expr: Term): BigDecimal = {
    expr match {
      case x: Num => x.getValue
      case t: Fun =>
        {
          val l = t.args.length
          val rs = new Array[BigDecimal](l)
          var ok = true
          for (i <- 0 until l) {
            rs(i) = eval(t.getArg(i))
            if (rs(i).eq(null)) ok = false
          }
          if (!ok) null
          else if (2 == l) {
            val x = rs(0)
            val y = rs(1)
            t.sym match {
              case "+" => x + y
              case "-" => x - y
              case "*" => x * y
              case "/" => x / y
              case "//" => BigDecimal(x.toBigInt / y.toBigInt)
              case "div" => BigDecimal(x.toBigInt / y.toBigInt)
              case "%" => BigDecimal(x.toBigInt.mod(y.toBigInt))
              case "?" => if (x < y) -1 else if (x == y) 0 else 1
              case "**" => x.pow(y.toInt)
              case "^" => x.pow(y.toInt)
              case "log" => BigDecimal(math.log(x.doubleValue / math.log(y.doubleValue)))
              case "<<" => BigDecimal(x.toBigInt << y.toInt)
              case ">>" => BigDecimal(x.toBigInt >> y.toInt)
              case "xor" => BigDecimal(x.toBigInt ^ y.toInt)
              case """/\""" => BigDecimal(x.toBigInt & y.toInt)
              case """\/""" => BigDecimal(x.toBigInt | y.toInt)
              case "gcd" => BigDecimal(x.toBigInt gcd y.toInt)
              case "getbit" => if (x.toBigInt.testBit(y.toInt)) 1 else 0
              case _ => null
            }
          } else if (1 == l) {
            val x = rs(0)
            t.sym match {
              case "abs" => x.abs
              case "floor" => BigDecimal(x.toBigInt)
              case "ceiling" => BigDecimal(x.toBigInt + 1)
              case "lsb" => BigDecimal(x.toBigInt.lowestSetBit)
              case "bitcount" => BigDecimal(x.toBigInt.bitCount)
              case "random" => BigDecimal((new Random()).nextInt(x.toIntExact))
              case _ => null
            }
          } else if (3 == l) {
            val x = rs(0)
            val y = rs(1)
            val z = rs(2)
            t.sym match {
              case "setbit" => {
                val n = x.toBigInt
                BigDecimal(
                  if (0 == z.toInt) n.clearBit(y.toInt) else n.setBit(y.toInt))
              }
              case _ => null
            }

          } else null
        }
      case c: Const => c.sym match {
        case "random" => math.random
        case "pi" => math.Pi
        case "e" => math.E
        case _ => null
      }
      case _: Term => null
    }
  }

  override def exec(p: Prog) = {
    val r = eval(getArg(1))
    if (null == r) IO.errmes("bad arithmetic operation", this, p)
    else putArg(0, Real(r), p)
  }
}