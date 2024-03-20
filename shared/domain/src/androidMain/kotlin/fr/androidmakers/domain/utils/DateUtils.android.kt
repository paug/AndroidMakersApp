package fr.androidmakers.domain.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import java.text.DateFormat.SHORT
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale
import java.util.TimeZone

actual fun LocalDateTime.formatShortTime(): String {
  return this.toInstant(kotlinx.datetime.TimeZone.currentSystemDefault()).formatShortTime()
}

actual fun Instant.formatShortTime(): String {
  val dateFormat = SimpleDateFormat.getTimeInstance(SHORT)

  // Display the time in the local time as everyone will be on site so it makes
  // planning in advance easier
  val tz = TimeZone.getTimeZone("Europe/Paris")
  if (tz != null) {
    dateFormat.timeZone = tz
  }

  val date = Date(this.toEpochMilliseconds())
  return dateFormat.format(date).lowercase(Locale.getDefault())
}
