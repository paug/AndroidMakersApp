package fr.androidmakers.domain.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSDateFormatterLongStyle
import platform.Foundation.NSDateFormatterMediumStyle

actual fun LocalDateTime.formatShortTime(): String {
  val date = toInstant(TimeZone.currentSystemDefault())
  return date.formatShortTime()
}

actual fun Instant.formatShortTime(): String {
  val date = toNSDate()
  val dateFormatter = NSDateFormatter().apply {
    dateStyle = NSDateFormatterLongStyle
    timeStyle = NSDateFormatterMediumStyle
  }
  return dateFormatter.stringFromDate(date)
}
