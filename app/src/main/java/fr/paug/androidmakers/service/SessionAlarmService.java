package fr.paug.androidmakers.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.AlarmManagerCompat;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.util.Log;

import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Room;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.model.Session;
import fr.paug.androidmakers.receiver.SessionAlarmReceiver;
import fr.paug.androidmakers.ui.activity.MainActivity;
import fr.paug.androidmakers.util.SessionSelector;

public class SessionAlarmService extends JobIntentService {

    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 1000;
    private static final String TAG = "sessionAlarm";

    public static final String ACTION_NOTIFY_SESSION = "NOTIFY_SESSION";

    public static final String ACTION_SCHEDULE_STARRED_BLOCK = "SCHEDULE_STARRED_BLOCK";
    public static final String ACTION_UNSCHEDULE_UNSTARRED_BLOCK = "ACTION_UNSCHEDULE_UNSTARRED_BLOCK";
    public static final String ACTION_SCHEDULE_ALL_STARRED_BLOCKS = "SCHEDULE_ALL_STARRED_BLOCKS";

    public static final String EXTRA_SESSION_ID = "SESSION_ID";
    public static final String EXTRA_SESSION_START = "SESSION_START";
    public static final String EXTRA_SESSION_END = "SESSION_END";
    public static final String EXTRA_NOTIFICATION_TITLE = "NOTIF_TITLE";
    public static final String EXTRA_NOTIFICATION_CONTENT = "NOTIF_CONTENT";

    private static final int NOTIFICATION_LED_ON_MS = 100;
    private static final int NOTIFICATION_LED_OFF_MS = 1000;
    private static final int NOTIFICATION_ARGB_COLOR = 0xff1EB6E1;

    private static final long FIVE_MINUTES_IN_MILLISECONDS = 300000;

    private static final long UNDEFINED_VALUE = -1;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SessionAlarmService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        final String action = intent.getAction();
        logDebug("Session alarm : " + action);

        if (ACTION_SCHEDULE_ALL_STARRED_BLOCKS.equals(action)) {
            scheduleAllStarredSessions();
            return;
        }

        final long sessionEnd = intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_END, UNDEFINED_VALUE);
        final long sessionStart = intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_START, UNDEFINED_VALUE);
        final int sessionId = intent.getIntExtra(SessionAlarmService.EXTRA_SESSION_ID, 0);

        if (ACTION_NOTIFY_SESSION.equals(action)) {
            final String notificationTitle = intent.getStringExtra(SessionAlarmService.EXTRA_NOTIFICATION_TITLE);
            final String notificationContent = intent.getStringExtra(SessionAlarmService.EXTRA_NOTIFICATION_CONTENT);
            if (notificationTitle == null || notificationContent == null) {
                Log.w(TAG, "Title or content of the notification is null.");
                return;
            }
            logDebug("Notifying about sessions starting at " +
                    sessionStart + " = " + (new Date(sessionStart)).toString());
            notifySession(sessionId, notificationTitle, notificationContent);
        } else if (ACTION_SCHEDULE_STARRED_BLOCK.equals(action)) {
            logDebug("Scheduling session alarm.");
            logDebug("-> Session start: " + sessionStart + " = " + (new Date(sessionStart))
                    .toString());
            logDebug("-> Session end: " + sessionEnd + " = " + (new Date(sessionEnd)).toString());
            scheduleAlarm(sessionStart, sessionEnd, sessionId, true);
        } else if (ACTION_UNSCHEDULE_UNSTARRED_BLOCK.equals(action)) {
            logDebug("Unscheduling session alarm for id " + sessionId);
            unscheduleAlarm(sessionId);
        }
    }

    void scheduleAllStarredSessions() {
        logDebug("scheduleAllStarredSessions");
        final CountDownLatch latch = new CountDownLatch(1);
        // need to be sure that the AgendaRepository is loaded in order to schedule all starred sessions
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<ScheduleSlot> scheduleSlots = AgendaRepository.getInstance()
                        .getScheduleSlots();

                // first unschedule all sessions
                // this is done in case the session slot has changed
                for (ScheduleSlot scheduleSlot : scheduleSlots) {
                    unscheduleAlarm(scheduleSlot.sessionId);
                }

                for (String id : SessionSelector.getInstance().getSessionsSelected()) {
                    String sessionYearId = id.replace(AgendaRepository.CURRENT_YEAR_NODE + "_", "");
                    logDebug("session id without year: " + sessionYearId);
                    ScheduleSlot scheduleSlot = AgendaRepository.getInstance().getScheduleSlot(sessionYearId);

                    if (scheduleSlot != null) {
                        Log.i("SessionAlarmService", scheduleSlot.toString());
                        scheduleAlarm(scheduleSlot.startDate, scheduleSlot.endDate,
                                scheduleSlot.sessionId, false);
                    }
                }
                latch.countDown();
            }
        };
        AgendaRepository.getInstance().load(new AgendaLoadListener(runnable));
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
    }

    /**
     * Schedule an alarm for a given session that begins at a given time
     *
     * @param sessionStart    start time of the slot. The alarm will be fired before this time
     * @param sessionEnd      end time of the slot
     * @param sessionId       id of the session
     * @param allowLastMinute allow or not the alarm to be set if the delay between the alarm and
     *                        the session start is over.
     */
    private void scheduleAlarm(final long sessionStart,
                               final long sessionEnd,
                               int sessionId,
                               boolean allowLastMinute) {
        final long currentTime = System.currentTimeMillis();

        Log.i("Time", "current: " + currentTime + ", session start: " + sessionStart);

        final long alarmTime = sessionStart - FIVE_MINUTES_IN_MILLISECONDS;

        if (allowLastMinute) {
            // If the session is already started, do not schedule system notification.
            if (currentTime > sessionStart) {
                logDebug("Not scheduling alarm because target time is in the past: " + sessionStart);
                return;
            }
        } else {
            if (currentTime > alarmTime) {
                logDebug("Not scheduling alarm because alarm time is in the past: " + alarmTime);
                return;
            }
        }

        final Session sessionToNotify = AgendaRepository.getInstance().getSession(sessionId);
        final ScheduleSlot slotToNotify = AgendaRepository.getInstance().getScheduleSlot(sessionId);
        if (sessionToNotify == null || slotToNotify == null) {
            Log.w(TAG, "Cannot find session " + sessionId + " either in sessions or in slots");
            return;
        }

        final String sessionDate = DateUtils.formatDateRange(this,
                new Formatter(Locale.getDefault()),
                slotToNotify.startDate,
                slotToNotify.endDate,
                DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_ABBREV_WEEKDAY | DateUtils.FORMAT_SHOW_TIME,
                null).toString();

        final Room room = AgendaRepository.getInstance().getRoom(slotToNotify.room);
        final String roomName = ((room != null) ? room.name : "") + " - ";

        logDebug("Scheduling alarm for " + alarmTime + " = " + (new Date(alarmTime)).toString());

        final Intent notificationIntent = new Intent(
                ACTION_NOTIFY_SESSION,
                Uri.parse(String.valueOf(sessionId)),
                this,
                SessionAlarmReceiver.class);
        notificationIntent.putExtra(EXTRA_SESSION_START, sessionStart);
        logDebug("-> Intent extra: session start " + sessionStart);
        notificationIntent.putExtra(EXTRA_SESSION_END, sessionEnd);
        logDebug("-> Intent extra: session end " + sessionEnd);
        notificationIntent.putExtra(EXTRA_SESSION_ID, sessionId);
        notificationIntent.putExtra(EXTRA_NOTIFICATION_TITLE, sessionToNotify.title);
        notificationIntent.putExtra(EXTRA_NOTIFICATION_CONTENT, roomName + sessionDate);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, sessionId, notificationIntent, 0);

        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Schedule an alarm to be fired to notify user of added sessions are about to begin.
        logDebug("-> Scheduling RTC_WAKEUP alarm at " + alarmTime);
        AlarmManagerCompat.setExact(alarmManager, AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
    }

    /**
     * Remove the scheduled alarm for a given session
     *
     * @param sessionId the id of the session
     */
    private void unscheduleAlarm(int sessionId) {
        final AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        final Intent notifIntent = new Intent(
                ACTION_NOTIFY_SESSION,
                Uri.parse(String.valueOf(sessionId)),
                this,
                SessionAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, sessionId, notifIntent, 0);

        am.cancel(pi);
    }

    // Starred sessions are about to begin. Constructs and triggers system notification.
    private void notifySession(int sessionId,
                               @NonNull String notificationTitle,
                               @NonNull String notificationContent) {
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

        String channelId = "Sessions";

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
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

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Sessions about to begin",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        logDebug("Now showing notification.");
        notificationManager.notify(sessionId, notificationBuilder.build());
    }

    public static void logDebug(String message) {
        Log.d(TAG, message);
    }

    private static class AgendaLoadListener implements AgendaRepository.OnLoadListener {
        @NonNull
        private final Runnable runnable;

        private AgendaLoadListener(@NonNull Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void onAgendaLoaded() {
            runnable.run();
            AgendaRepository.getInstance().removeListener(this);
        }
    }

}
