package prolog.fluents

import scala.collection.mutable._
import prolog.terms._

import scala.collection.mutable

class Deque[T] extends mutable.Queue[T] {
  def push(x: T) = prepend(x)
  def add(x: T) = append(x)
}