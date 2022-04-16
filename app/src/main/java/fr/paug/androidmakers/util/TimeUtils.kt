package fr.paug.androidmakers.util

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.material.contentColorFor
import fr.paug.androidmakers.R
import java.time.Duration
import java.time.OffsetDateTime
import java.util.*

object TimeUtils {
    const val SECOND = 1000
    const val MINUTE = 60 * SECOND
    const val HOUR = 60 * MINUTE

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

    fun formatDuration(context: Context, duration: Long): String {
        val minutes = duration / MINUTE
        return if (minutes <= 120) {
            context.resources.getQuantityString(
                R.plurals.duration_minutes, minutes.toInt(),
                minutes
            )
        } else {
            context.resources.getString(R.string.many_minutes)
        }
    }
}