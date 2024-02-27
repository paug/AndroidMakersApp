package fr.paug.androidmakers.util

import android.content.Context
import android.text.format.DateFormat
import dev.icerock.moko.resources.desc.Plural
import dev.icerock.moko.resources.desc.PluralFormatted
import dev.icerock.moko.resources.desc.StringDesc
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.MR
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.time.Duration

object TimeUtils {
  const val SECOND = 1000
  const val MINUTE = 60 * SECOND
  const val HOUR = 60 * MINUTE

  fun parseIso8601(iso8601: String): Date {
    return Date(OffsetDateTime.parse(iso8601).toEpochSecond() * 1000)
  }

  /**
   * format the time as HH:mm in the timezone of the event
   */
  fun formatShortTime(context: Context?, time: Date?): String {

    // Android DateFormatter will honor the user's current settings.
    val dateFormat = DateFormat.getTimeFormat(context)

    // Display the time in the local time as everyone will be on site so it makes
    // planning in advance easier
    val tz = TimeZone.getTimeZone("Europe/Paris")
    if (tz != null) {
      dateFormat.timeZone = tz
    }
    return dateFormat.format(time).lowercase(Locale.getDefault())
  }

  fun formatDuration(context: Context, duration: Duration): String {
    val minutes = duration.inWholeMinutes
    return if (minutes <= 120) {
      StringDesc.PluralFormatted(
          MR.plurals.duration_minutes, minutes.toInt(),
          minutes
      ).toString(context)
    } else {
      context.resources.getString(R.string.many_minutes)
    }
  }
}

val eventTimeZone = kotlinx.datetime.TimeZone.of("Europe/Paris")
