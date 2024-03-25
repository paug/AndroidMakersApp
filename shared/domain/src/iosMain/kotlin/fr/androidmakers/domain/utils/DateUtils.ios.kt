package fr.androidmakers.domain.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterLongStyle
import platform.Foundation.NSDateFormatterMediumStyle
import platform.Foundation.NSDateFormatterShortStyle
import platform.Foundation.NSDateInterval
import platform.Foundation.NSDateIntervalFormatter

actual fun LocalDateTime.formatShortTime(): String {
  val date = toInstant(TimeZone.currentSystemDefault())
  return date.formatShortTime()
}

actual fun Instant.formatShortTime(): String {
  val date = toNSDate()
  val dateFormatter = NSDateFormatter().apply {
    timeStyle = NSDateFormatterShortStyle
  }
  return dateFormatter.stringFromDate(date)
}

actual fun LocalDate.formatMediumDate(): String {
  val date = atStartOfDayIn(TimeZone.currentSystemDefault())
  val nsDate = date.toNSDate()
  val dateFormatter = NSDateFormatter().apply {
    dateStyle = NSDateFormatterMediumStyle
  }
  return dateFormatter.stringFromDate(nsDate)
}

actual fun formatTimeInterval(startDate: LocalDateTime, endDate: LocalDateTime): String {
  val datFormatter = NSDateIntervalFormatter().apply {
    timeStyle = NSDateFormatterShortStyle
  }

  val startNSDate = startDate.toInstant(TimeZone.currentSystemDefault()).toNSDate()
  val endNSDate = endDate.toInstant(TimeZone.currentSystemDefault()).toNSDate()

  return datFormatter.stringFromDateInterval(NSDateInterval(startNSDate, endNSDate)) ?: ""
}
