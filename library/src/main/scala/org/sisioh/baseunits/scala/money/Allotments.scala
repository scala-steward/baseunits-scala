package org.sisioh.baseunits.scala.money

object Allotments {

  def empty[T]: Allotments[T] = new Allotments(Set.empty)

  def apply[T](values: Allotment[T]*): Allotments[T] =
    new Allotments(values.toSet)
}

class Allotments[T](private val values: Set[Allotment[T]]) {

  def negated: Allotments[T] = new Allotments(values.map(_.negated))

  def filter(p: Allotment[T] => Boolean): Allotments[T] =
    new Allotments(values.filter(p))

  def find(p: Allotment[T] => Boolean): Option[Allotment[T]] = values.find(p)

  def combine(other: Allotments[T]): Allotments[T] =
    new Allotments(values ++ other.values)

  def ++(other: Allotments[T]): Allotments[T] = combine(other)

  def toIterator: Iterator[Allotment[T]] = values.iterator

  def toSeq: Seq[Allotment[T]] = values.toSeq

  def toSet: Set[Allotment[T]] = values

  def toVector: Vector[Allotment[T]] = values.toVector

  def canEqual(other: Any): Boolean = other.isInstanceOf[Allotments[T]]

  override def equals(other: Any): Boolean = other match {
    case that: Allotments[T] =>
      (that canEqual this) &&
        values == that.values
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(values)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}
