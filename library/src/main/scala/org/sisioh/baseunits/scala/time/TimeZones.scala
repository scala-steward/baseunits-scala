package org.sisioh.baseunits.scala.time

import java.util.TimeZone

object TimeZones {

  val Default: TimeZone = TimeZone.getDefault

  val GMT: TimeZone = TimeZone.getTimeZone("Universal")

  val JST: TimeZone = TimeZone.getTimeZone("Asia/Tokyo")

}
