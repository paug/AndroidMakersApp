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

    public static final int NOTIFICATION_ID = 100;

    private static final long MILLI_TEN_MINUTES = 600000;
    private static final long MILLI_FIVE_MINUTES = 300000;
    private static final long MILLI_ONE_MINUTE = 60000;

    private static final long UNDEFINED_ALARM_OFFSET = -1;

    public SessionAlarmService() {
        super("SessionAlarmService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String action = intent.getAction();

        // TODO: 31/03/2017 handle actions

        scheduleAllStarredSessions();
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
}
