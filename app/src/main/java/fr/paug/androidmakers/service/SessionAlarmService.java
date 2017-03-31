package fr.paug.androidmakers.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import fr.paug.androidmakers.BuildConfig;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.util.SessionSelector;

/**
 * Created by benjamin on 30/03/2017.
 */

public class SessionAlarmService extends IntentService {

    public static final String ACTION_NOTIFY_SESSION = "notify";

    public static final String ACTION_SCHEDULE_STARRED_BLOCK =
            BuildConfig.APPLICATION_ID + ".action.SCHEDULE_STARRED_BLOCK";
    public static final String ACTION_SCHEDULE_ALL_STARRED_BLOCKS =
            BuildConfig.APPLICATION_ID + ".action.SCHEDULE_ALL_STARRED_BLOCKS";

    public static final String EXTRA_SESSION_START =
            BuildConfig.APPLICATION_ID + ".extra.SESSION_START";
    public static final String EXTRA_SESSION_END =
            BuildConfig.APPLICATION_ID + ".extra.SESSION_END";

    public static final int NOTIFICATION_ID = 100;

    private static final long MILLI_TEN_MINUTES = 600000;
    private static final long MILLI_FIVE_MINUTES = 300000;
    private static final long MILLI_ONE_MINUTE = 60000;

    private static final long UNDEFINED_ALARM_OFFSET = -1;
    private static final long UNDEFINED_VALUE = -1;

    public SessionAlarmService() {
        super("SessionAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();

        // TODO: 31/03/2017 handle actions

        if (ACTION_SCHEDULE_ALL_STARRED_BLOCKS.equals(action)) {
            //Scheduling all starred blocks.
            scheduleAllStarredSessions();
            return;
        }

        final long sessionEnd = intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_END,
                UNDEFINED_VALUE);
        if (sessionEnd == UNDEFINED_VALUE) {
            return;
        }

        final long sessionStart =
                intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_START, UNDEFINED_VALUE);
        if (sessionStart == UNDEFINED_VALUE) {
            return;
        }

        if (ACTION_NOTIFY_SESSION.equals(action)) {
//            LOGD(TAG, "Notifying about sessions starting at " +
//                    sessionStart + " = " + (new Date(sessionStart)).toString());
//            LOGD(TAG, "-> Alarm offset: " + sessionAlarmOffset);
            notifySession(sessionStart, MILLI_FIVE_MINUTES);
        } else if (ACTION_SCHEDULE_STARRED_BLOCK.equals(action)) {
//            LOGD(TAG, "Scheduling session alarm.");
//            LOGD(TAG, "-> Session start: " + sessionStart + " = " + (new Date(sessionStart))
//                    .toString());
//            LOGD(TAG, "-> Session end: " + sessionEnd + " = " + (new Date(sessionEnd)).toString());
//            LOGD(TAG, "-> Alarm offset: " + sessionAlarmOffset);
            scheduleAlarm(sessionStart, sessionEnd, MILLI_FIVE_MINUTES);
        }
    }

    void scheduleAllStarredSessions() {
        List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance().getScheduleSlots();

        for (String id : SessionSelector.getInstance().getSessionsSelected()) {
            for (ScheduleSlot scheduleSlot : scheduleSlots) {
                if (String.valueOf(scheduleSlot.sessionId) == id) {
                    Log.i("SessionAlarmService", scheduleSlot.toString());
                    scheduleAlarm(scheduleSlot.startDate, scheduleSlot.endDate, MILLI_FIVE_MINUTES);
                }
            }
        }
    }

    private void scheduleAlarm(final long sessionStart,
                               final long sessionEnd, final long alarmOffset) {
        NotificationManager nm =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(NOTIFICATION_ID);
        final long currentTime = System.currentTimeMillis();

        Log.i("Time", "current: "+currentTime+", session start: "+sessionStart);

        // If the session is already started, do not schedule system notification.
        if (currentTime > sessionStart) {
            return;
        }

        // By default, sets alarm to go off at 10 minutes before session start time.  If alarm
        // offset is provided, alarm is set to go off by that much time from now.
        long alarmTime;
        if (alarmOffset == UNDEFINED_ALARM_OFFSET) {
            alarmTime = sessionStart - MILLI_TEN_MINUTES;
        } else {
            alarmTime = currentTime + alarmOffset;
        }

        final Intent notifIntent = new Intent(
                ACTION_NOTIFY_SESSION,
                null,
                this,
                SessionAlarmService.class);
        notifIntent.putExtra("EXTRA_SESSION_START", sessionStart);
        notifIntent.putExtra("EXTRA_SESSION_END", sessionEnd);
        notifIntent.putExtra("EXTRA_SESSION_ALARM_OFFSET", alarmOffset);
        PendingIntent pi = PendingIntent.getService(this,
                0,
                notifIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        final AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Schedule an alarm to be fired to notify user of added sessions are about to begin.
        am.set(AlarmManager.RTC_WAKEUP, alarmTime, pi);
    }

    // Starred sessions are about to begin.  Constructs and triggers system notification.
    private void notifySession(final long sessionStart, final long alarmOffset) {
////        long currentTime = TimeUtils.getCurrentTime(this);
//        long currentTime = System.currentTimeMillis();
//        final long intervalEnd = sessionStart + MILLI_TEN_MINUTES;
//        LOGD(TAG, "Considering notifying for time interval.");
//        LOGD(TAG, "    Interval start: " + sessionStart + "=" + (new Date(sessionStart)).toString());
//        LOGD(TAG, "    Interval end: " + intervalEnd + "=" + (new Date(intervalEnd)).toString());
//        LOGD(TAG, "    Current time is: " + currentTime + "=" + (new Date(currentTime)).toString());
//        if (sessionStart < currentTime) {
//            LOGD(TAG, "Skipping session notification (too late -- time interval already started)");
//            return;
//        }
//
//        if (!SettingsUtils.shouldShowSessionReminders(this)) {
//            // skip if disabled in settings
//            LOGD(TAG, "Skipping session notification for sessions. Disabled in settings.");
//            return;
//        }
//
//        // Avoid repeated notifications.
//        if (alarmOffset == UNDEFINED_ALARM_OFFSET && UIUtils.isNotificationFiredForBlock(
//                this, ScheduleContract.Blocks.generateBlockId(sessionStart, intervalEnd))) {
//            LOGD(TAG, "Skipping session notification (already notified)");
//            return;
//        }
//
//        final ContentResolver cr = getContentResolver();
//
//        LOGD(TAG, "Looking for sessions in interval " + sessionStart + " - " + intervalEnd);
//        Cursor c = null;
//        try {
//            c = cr.query(
//                    ScheduleContract.Sessions.CONTENT_MY_SCHEDULE_URI,
//                    SessionDetailQuery.PROJECTION,
//                    ScheduleContract.Sessions.STARTING_AT_TIME_INTERVAL_SELECTION,
//                    ScheduleContract.Sessions.buildAtTimeIntervalArgs(sessionStart, intervalEnd),
//                    null);
//            int starredCount = c.getCount();
//            LOGD(TAG, "# starred sessions in that interval: " + c.getCount());
//            String singleSessionId = null;
//            String singleSessionRoomId = null;
//            ArrayList<String> starredSessionTitles = new ArrayList<String>();
//            while (c.moveToNext()) {
//                singleSessionId = c.getString(SessionDetailQuery.SESSION_ID);
//                singleSessionRoomId = c.getString(SessionDetailQuery.ROOM_ID);
//                starredSessionTitles.add(c.getString(SessionDetailQuery.SESSION_TITLE));
//                LOGD(TAG, "-> Title: " + c.getString(SessionDetailQuery.SESSION_TITLE));
//            }
//            if (starredCount < 1) {
//                return;
//            }
//
//            // Generates the pending intent which gets fired when the user taps on the notification.
//            // NOTE: Use TaskStackBuilder to comply with Android's design guidelines
//            // related to navigation from notifications.
//            Intent baseIntent = new Intent(this, MyScheduleActivity.class);
//            baseIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            TaskStackBuilder taskBuilder = TaskStackBuilder.create(this)
//                    .addNextIntent(baseIntent);
//
//            // For a single session, tapping the notification should open the session details (b/15350787)
//            if (starredCount == 1) {
//                taskBuilder.addNextIntent(new Intent(Intent.ACTION_VIEW,
//                        ScheduleContract.Sessions.buildSessionUri(singleSessionId)));
//            }
//
//            PendingIntent pi = taskBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
//
//            final Resources res = getResources();
//            String contentText;
//            int minutesLeft = (int) (sessionStart - currentTime + 59000) / 60000;
//            if (minutesLeft < 1) {
//                minutesLeft = 1;
//            }
//
//            if (starredCount == 1) {
//                contentText = res.getString(R.string.session_notification_text_1, minutesLeft);
//            } else {
//                contentText = res.getQuantityString(R.plurals.session_notification_text,
//                        starredCount - 1,
//                        minutesLeft,
//                        starredCount - 1);
//            }
//
//            NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this)
//                    .setContentTitle(starredSessionTitles.get(0))
//                    .setContentText(contentText)
//                    .setColor(getResources().getColor(R.color.theme_primary))
//                    .setTicker(res.getQuantityString(R.plurals.session_notification_ticker,
//                            starredCount,
//                            starredCount))
//                    .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
//                    .setLights(
//                            SessionAlarmService.NOTIFICATION_ARGB_COLOR,
//                            SessionAlarmService.NOTIFICATION_LED_ON_MS,
//                            SessionAlarmService.NOTIFICATION_LED_OFF_MS)
//                    .setSmallIcon(R.drawable.ic_stat_notification)
//                    .setContentIntent(pi)
//                    .setPriority(Notification.PRIORITY_MAX)
//                    .setAutoCancel(true);
//            if (minutesLeft > 5) {
//                notifBuilder.addAction(R.drawable.ic_stat_alarm,
//                        String.format(res.getString(R.string.snooze_x_min), 5),
//                        createSnoozeIntent(sessionStart, intervalEnd, 5));
//            }
//            if (starredCount == 1 && SettingsUtils.isAttendeeAtVenue(this)) {
//                notifBuilder.addAction(R.drawable.ic_stat_map,
//                        res.getString(R.string.title_map),
//                        createRoomMapIntent(singleSessionRoomId));
//            }
//            String bigContentTitle;
//            if (starredCount == 1 && starredSessionTitles.size() > 0) {
//                bigContentTitle = starredSessionTitles.get(0);
//            } else {
//                bigContentTitle = res.getQuantityString(R.plurals.session_notification_title,
//                        starredCount,
//                        minutesLeft,
//                        starredCount);
//            }
//            NotificationCompat.InboxStyle richNotification = new NotificationCompat.InboxStyle(
//                    notifBuilder)
//                    .setBigContentTitle(bigContentTitle);
//
//            // Adds starred sessions starting at this time block to the notification.
//            for (int i = 0; i < starredCount; i++) {
//                richNotification.addLine(starredSessionTitles.get(i));
//            }
//            NotificationManager nm = (NotificationManager) getSystemService(
//                    Context.NOTIFICATION_SERVICE);
//            LOGD(TAG, "Now showing notification.");
//            nm.notify(NOTIFICATION_ID, richNotification.build());
//        } finally {
//            if (c != null) { try { c.close(); } catch (Exception ignored) { } }
//        }
    }

}
