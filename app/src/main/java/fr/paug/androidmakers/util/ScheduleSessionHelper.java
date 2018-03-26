package fr.paug.androidmakers.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.service.SessionAlarmService;
import fr.paug.androidmakers.ui.adapter.ScheduleSession;

public class ScheduleSessionHelper {

    public static final long ALLOWED_OVERLAP = 5 * 60 * 1000; // 5 minutes

    public static boolean sameStartTime(ScheduleSession block1, ScheduleSession block2, boolean useOverlap) {
        return Math.abs(block1.getStartTimestamp() - block2.getStartTimestamp()) <= (useOverlap ? ALLOWED_OVERLAP : 0);
    }

    //region Scheduling session
    public static void scheduleStarredSession(Context context, long sessionStartDateInMillis, long sessionEndDateInMillis, int sessionId) {
        Log.d("Schedule session", "Scheduling notification for session " + sessionId);
        Toast.makeText(context, R.string.session_selected, Toast.LENGTH_SHORT).show();

        final Intent scheduleIntent = new Intent(
                SessionAlarmService.ACTION_SCHEDULE_STARRED_BLOCK,
                null, context, SessionAlarmService.class);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_START, sessionStartDateInMillis);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_END, sessionEndDateInMillis);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_ID, sessionId);
        context.startService(scheduleIntent);
    }

    public static void unScheduleSession(Context context, int sessionId) {
        Log.d("Schedule session", "Unscheduling notification for session " + sessionId);
        Toast.makeText(context, R.string.session_deselected, Toast.LENGTH_SHORT).show();

        final Intent scheduleIntent = new Intent(
                SessionAlarmService.ACTION_UNSCHEDULE_UNSTARRED_BLOCK,
                null, context, SessionAlarmService.class);
        scheduleIntent.putExtra(SessionAlarmService.EXTRA_SESSION_ID, sessionId);
        context.startService(scheduleIntent);
    }
    //endregion

}