package fr.paug.androidmakers.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import fr.paug.androidmakers.R;

public class TimeUtils {

    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    public static String formatShortTime(Context context, Date time) {
        // Android DateFormatter will honor the user's current settings.
        DateFormat format = android.text.format.DateFormat.getTimeFormat(context);
        // Override with Timezone based on settings since users can override their phone's timezone
        // with Pacific time zones.
        TimeZone tz = TimeZone.getDefault();
        if (tz != null) {
            format.setTimeZone(tz);
        }
        return format.format(time).toLowerCase(Locale.getDefault());
    }

    public static String formatDuration(@NonNull Context context, long startTime, long endTime) {
        return formatDuration(context, endTime - startTime);
    }

    public static String formatDuration(@NonNull Context context, long duration) {
        Float hours = duration / (float) HOUR;
        if (hours >= 1f) {
            return context.getResources().getQuantityString(R.plurals.duration_hours,
                    (int) Math.ceil(hours), (hours == hours.intValue()) ?
                            String.valueOf(hours.intValue()) : hours.toString());
        } else {
            long minutes = duration / MINUTE;
            return context.getResources().getQuantityString(
                    R.plurals.duration_minutes, (int) minutes, minutes);
        }
    }

}
