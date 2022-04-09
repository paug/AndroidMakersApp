package fr.paug.androidmakers.util

import android.content.Context
import android.text.format.DateFormat
import fr.paug.androidmakers.R
import java.time.OffsetDateTime
import java.util.*

object TimeUtils {
    const val SECOND = 1000
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE
    const val DAY = 24 * HOUR

    fun parseIso8601(iso8601: String): Date {
        return Date(OffsetDateTime.parse(iso8601).toEpochSecond() * 1000)
    }
    fun formatShortTime(context: Context?, time: Date?): String {
        // Android DateFormatter will honor the user's current settings.
        val dateFormat = DateFormat.getTimeFormat(context)
        // Override with Timezone based on settings since users can override their phone's timezone
        // with Pacific time zones.
        val tz = TimeZone.getDefault()
        if (tz != null) {
            dateFormat.timeZone = tz
        }
        return dateFormat.format(time).lowercase(Locale.getDefault())
    }

    fun formatDuration(context: Context, startTime: Long, endTime: Long): String {
        return formatDuration(context, endTime - startTime)
    }

    private fun formatDuration(context: Context, duration: Long): String {
        val hours = duration / HOUR.toFloat()
        return if (hours >= 1f) {
            context.resources.getQuantityString(
                R.plurals.duration_hours, Math.ceil(hours.toDouble()).toInt(),
                if (hours == hours.toInt().toFloat()) hours.toInt()
                    .toString() else (Math.round(hours * 100.0) / 100.0).toString()
            )
        } else {
            val minutes = duration / MINUTE
            context.resources.getQuantityString(
                R.plurals.duration_minutes, minutes.toInt(),
                minutes
            )
        }
    }
}