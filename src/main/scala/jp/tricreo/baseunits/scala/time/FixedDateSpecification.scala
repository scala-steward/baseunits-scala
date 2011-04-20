package jp.tricreo.baseunits.scala.time

import jp.tricreo.baseunits.scala.intervals.Limit

/**
 * Created by IntelliJ IDEA.
 * User: junichi
 * Date: 11/04/19
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */

class FixedDateSpecification private[time]
(private[time] val date: CalendarDate) extends DateSpecification {

  def iterateOver(interval: CalendarInterval): Iterator[CalendarDate] = {
    if (firstOccurrenceIn(interval) == None) {
      return Iterator.empty
    }
    new Iterator[CalendarDate] {

      var end: Boolean = _

      override def hasNext = end

      override def next = {
        if (hasNext == false) {
          throw new NoSuchElementException
        }
        end = true
        date
      }
    }
  }


  override def isSatisfiedBy(date: CalendarDate): Boolean =
    date == this.date

  def firstOccurrenceIn(interval: CalendarInterval): Option[CalendarDate] =
    if (interval.includes(Limit(date))) Some(date)
    else None
}