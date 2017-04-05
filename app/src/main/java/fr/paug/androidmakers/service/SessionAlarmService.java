package fr.paug.androidmakers.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.Date;
import java.util.Formatter;
import java.util.List;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.ui.activity.MainActivity;
import fr.paug.androidmakers.util.SessionSelector;

public class SessionAlarmService extends IntentService {

    private static final String TAG = "sessionAlarm";

    public static final String ACTION_NOTIFY_SESSION = "NOTIFY_SESSION";
    public static final String ACTION_REMOVE_NOTIFY_SESSION = "REMOVE_NOTIFY_SESSION";

    public static final String ACTION_SCHEDULE_STARRED_BLOCK = "SCHEDULE_STARRED_BLOCK";
    public static final String ACTION_SCHEDULE_ALL_STARRED_BLOCKS = "SCHEDULE_ALL_STARRED_BLOCKS";

    public static final String EXTRA_SESSION_ID = "SESSION_ID";
    public static final String EXTRA_SESSION_START = "SESSION_START";
    public static final String EXTRA_SESSION_END = "SESSION_END";

    public static final String EXTRA_SESSION_ALARM_OFFSET = "SESSION_ALARM_OFFSET";

    public static final String EXTRA_SESSION_ADD = "SESSION_ADD";
    public static final String EXTRA_SESSION_REMOVE = "SESSION_REMOVE";

    public static final int NOTIFICATION_ID = 100;

    private static final int NOTIFICATION_LED_ON_MS = 100;
    private static final int NOTIFICATION_LED_OFF_MS = 1000;
    private static final int NOTIFICATION_ARGB_COLOR = 0xff1EB6E1;

    private static final long MILLI_TEN_MINUTES = 600000;
    private static final long MILLI_FIVE_MINUTES = 300000;

    private static final long UNDEFINED_ALARM_OFFSET = -1;
    private static final long UNDEFINED_VALUE = -1;

    public SessionAlarmService() {
        super("SessionAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();
        LOGD(TAG, "Session alarm : " + action);

        if (ACTION_SCHEDULE_ALL_STARRED_BLOCKS.equals(action)) {
            //Scheduling all starred blocks.
            scheduleAllStarredSessions();
            return;
        }

        final long sessionEnd = intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_END, UNDEFINED_VALUE);
        final long sessionStart = intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_START, UNDEFINED_VALUE);
        final int sessionId = intent.getIntExtra(SessionAlarmService.EXTRA_SESSION_ID, 0);

        if (ACTION_NOTIFY_SESSION.equals(action)) {
            LOGD(TAG, "Notifying about sessions starting at " +
                    sessionStart + " = " + (new Date(sessionStart)).toString());
            notifySession(sessionStart);
        } else if (ACTION_SCHEDULE_STARRED_BLOCK.equals(action)) {
            LOGD(TAG, "Scheduling session alarm.");
            LOGD(TAG, "-> Session start: " + sessionStart + " = " + (new Date(sessionStart))
                    .toString());
            LOGD(TAG, "-> Session end: " + sessionEnd + " = " + (new Date(sessionEnd)).toString());
            scheduleAlarm(sessionStart, sessionEnd, sessionId, MILLI_FIVE_MINUTES);
        }
    }

    void scheduleAllStarredSessions() {
        List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();

        for (String id : SessionSelector.getInstance().getSessionsSelected()) {
            for (ScheduleSlot scheduleSlot : scheduleSlots) {
                if (String.valueOf(scheduleSlot.sessionId) == id) {
                    Log.i("SessionAlarmService", scheduleSlot.toString());
                    scheduleAlarm(scheduleSlot.startDate, scheduleSlot.endDate, scheduleSlot.sessionId, MILLI_FIVE_MINUTES);
                }
            }
        }
    }

    private void scheduleAlarm(final long sessionStart, final long sessionEnd, int sessionId, final long alarmOffset) {
        NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_ID);
        final long currentTime = System.currentTimeMillis();

        Log.i("Time", "current: " + currentTime + ", session start: " + sessionStart);

        // If the session is already started, do not schedule system notification.
        if (currentTime > sessionStart) {
            LOGD(TAG, "Not scheduling alarm because target time is in the past: " + sessionStart);
            return;
        }

        long alarmTime = sessionStart - MILLI_FIVE_MINUTES;

        LOGD(TAG, "Scheduling alarm for " + alarmTime + " = " + (new Date(alarmTime)).toString());

        final Intent notifIntent = new Intent(
                ACTION_NOTIFY_SESSION,
                null,
                this,
                SessionAlarmService.class);
        notifIntent.putExtra(EXTRA_SESSION_START, sessionStart);
        LOGD(TAG, "-> Intent extra: session start " + sessionStart);
        notifIntent.putExtra(EXTRA_SESSION_END, sessionEnd);
        LOGD(TAG, "-> Intent extra: session end " + sessionEnd);
        notifIntent.putExtra(EXTRA_SESSION_ALARM_OFFSET, alarmOffset);
        LOGD(TAG, "-> Intent extra: session alarm offset " + alarmOffset);

        PendingIntent pi = PendingIntent.getService(this, sessionId, notifIntent, PendingIntent.FLAG_ONE_SHOT);

        final AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Schedule an alarm to be fired to notify user of added sessions are about to begin.
        LOGD(TAG, "-> Scheduling RTC_WAKEUP alarm at " + alarmTime);
        am.set(AlarmManager.RTC_WAKEUP, alarmTime, pi);
    }

    // Starred sessions are about to begin.  Constructs and triggers system notification.
    private void notifySession(final long sessionStart) {
        long currentTime = System.currentTimeMillis();
        final long intervalEnd = sessionStart + MILLI_TEN_MINUTES;
        LOGD(TAG, "Considering notifying for time interval.");
        LOGD(TAG, "    Interval start: " + sessionStart + "=" + (new Date(sessionStart)).toString());
        LOGD(TAG, "    Interval end: " + intervalEnd + "=" + (new Date(intervalEnd)).toString());
        LOGD(TAG, "    Current time is: " + currentTime + "=" + (new Date(currentTime)).toString());

        // Find sessions details
        LOGD(TAG, "Looking for sessions in interval " + sessionStart + " - " + intervalEnd);

        List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();

        ScheduleSlot slotToNotify = null;
        for (ScheduleSlot scheduleSlot : scheduleSlots) {
            if (scheduleSlot.startDate == sessionStart) {
                Log.d(TAG, "schedule to start: " + scheduleSlot.toString());
                if (SessionSelector.getInstance().getSessionsSelected().contains(String.valueOf(scheduleSlot.sessionId))) {
                    Log.d(TAG, "starred schedule slot:" + scheduleSlot.sessionId);
                    slotToNotify = scheduleSlot;
                }
            }
        }

        Session sessionToNotify = AgendaRepository.getInstance().getSession(slotToNotify.sessionId);
        final String sessionDate = DateUtils.formatDateRange(this, new Formatter(getResources().getConfiguration().locale),
                slotToNotify.startDate,
                slotToNotify.endDate,
                DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY | DateUtils.FORMAT_SHOW_TIME,
                null).toString();

        // Generates the pending intent which gets fired when the user taps on the notification.
        Intent baseIntent = new Intent(this, MainActivity.class);
        baseIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(sessionToNotify.title)
                .setContentText(sessionDate)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setLights(
                        SessionAlarmService.NOTIFICATION_ARGB_COLOR,
                        SessionAlarmService.NOTIFICATION_LED_ON_MS,
                        SessionAlarmService.NOTIFICATION_LED_OFF_MS)
                .setSmallIcon(R.drawable.ic_event_note_white_24dp)
                .setContentIntent(resultPendingIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(
                Context.NOTIFICATION_SERVICE);
        LOGD(TAG, "Now showing notification.");
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void LOGD(final String tag, String message) {
        Log.d(tag, message);
    }

}
