package org.sisioh.baseunits.scala.time

import java.time.ZoneId
import java.util.TimeZone

object ZoneIds {

  val Default: ZoneId = ZoneId.systemDefault()

  val GMT: ZoneId = TimeZone.getTimeZone("Universal").toZoneId

  val JST: ZoneId = ZoneId.of("JST", ZoneId.SHORT_IDS)

}
