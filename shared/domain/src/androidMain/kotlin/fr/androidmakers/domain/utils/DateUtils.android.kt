@file:SuppressLint("NewApi")

package fr.androidmakers.domain.utils

import android.annotation.SuppressLint
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale
import java.util.concurrent.atomic.AtomicReference

/**
 * Memoize the result of the function call until the key changes.
 */
private fun <K, V> ((K) -> V).memoize() : (K) -> V {
  val cache = AtomicReference<Pair<K, V>?>(null)
  return { key: K ->
    val currentPair = cache.get()
    if (currentPair != null && currentPair.first == key) {
      currentPair.second
    } else {
      val newPair = key to this(key)
      // Only update the cache if it didn't change in the meantime
      cache.compareAndSet(currentPair, newPair)
      newPair.second
    }
  }
}

private val shortTimeFormatterProvider = { locale: Locale ->
  DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale)
}.memoize()

private val mediumDateFormatterProvider = { locale: Locale ->
  DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale)
}.memoize()

actual fun LocalDateTime.formatShortTime(): String {
  val shortTimeFormatter = shortTimeFormatterProvider(Locale.getDefault())
  return time.toJavaLocalTime().format(shortTimeFormatter).lowercase(Locale.getDefault())
}

actual fun Instant.formatShortTime(): String {
  // Display the time in the local time as everyone will be on site so it makes
  // planning in advance easier
  return toLocalDateTime(eventTimeZone).formatShortTime()
}

actual fun LocalDate.formatMediumDate(): String {
  val mediumDateFormatter = mediumDateFormatterProvider(Locale.getDefault())
  return toJavaLocalDate().format(mediumDateFormatter).lowercase(Locale.getDefault())
}

actual fun formatTimeInterval(startDate: LocalDateTime, endDate: LocalDateTime): String {

  return if (startDate.date == endDate.date) {
    "${startDate.date.formatMediumDate()}, ${startDate.formatShortTime()} - ${endDate.formatShortTime()}"
  } else {
    "${startDate.date.formatMediumDate()}, ${startDate.formatShortTime()} - ${endDate.date.formatMediumDate()}, ${endDate.formatShortTime()}"
  }
}
