package fr.paug.androidmakers.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.format.DateUtils
import android.util.Log
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import fr.paug.androidmakers.R
import fr.paug.androidmakers.manager.AndroidMakersStore
import fr.paug.androidmakers.receiver.SessionAlarmReceiver
import fr.paug.androidmakers.ui.activity.MainActivity
import fr.paug.androidmakers.util.SessionSelector
import fr.paug.androidmakers.util.TimeUtils
import java.text.SimpleDateFormat
import java.util.*

class SessionAlarmService : JobIntentService() {

    val format = SimpleDateFormat(TimeUtils.dateFormat)

    override fun onHandleWork(intent: Intent) {
        val action = intent.action
        logDebug("Session alarm : " + action!!)

        if (ACTION_SCHEDULE_ALL_STARRED_BLOCKS == action) {
            scheduleAllStarredSessions()
            return
        }

        val sessionEnd = intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_END, UNDEFINED_VALUE)
        val sessionStart = intent.getLongExtra(SessionAlarmService.EXTRA_SESSION_START, UNDEFINED_VALUE)
        val sessionId = intent.getStringExtra(SessionAlarmService.EXTRA_SESSION_ID)

        if (ACTION_NOTIFY_SESSION == action) {
            val notificationTitle = intent.getStringExtra(SessionAlarmService.EXTRA_NOTIFICATION_TITLE)
            val notificationContent = intent.getStringExtra(SessionAlarmService.EXTRA_NOTIFICATION_CONTENT)
            if (notificationTitle == null || notificationContent == null) {
                Log.w(TAG, "Title or content of the notification is null.")
                return
            }
            logDebug("Notifying about sessions starting at " +
                    sessionStart + " = " + Date(sessionStart).toString())
            notifySession(sessionId, notificationTitle, notificationContent)
        } else if (ACTION_SCHEDULE_STARRED_BLOCK == action) {
            logDebug("Scheduling session alarm.")
            logDebug("-> Session start: $sessionStart = " + Date(sessionStart)
                    .toString())
            logDebug("-> Session end: " + sessionEnd + " = " + Date(sessionEnd).toString())
            scheduleAlarm(sessionStart, sessionEnd, sessionId, true)
        } else if (ACTION_UNSCHEDULE_UNSTARRED_BLOCK == action) {
            logDebug("Unscheduling session alarm for id $sessionId")
            unscheduleAlarm(sessionId)
        }
    }

    internal fun scheduleAllStarredSessions() {
        logDebug("scheduleAllStarredSessions")
        // need to be sure that the AgendaRepository is loaded in order to schedule all starred sessions
        AndroidMakersStore().getSlotsFlow { scheduleSlots ->
            // first unschedule all sessions
            // this is done in case the session slot has changed
            for (scheduleSlot in scheduleSlots) {
                unscheduleAlarm(scheduleSlot.sessionId)
            }

            for (id in SessionSelector.sessionsSelected!!) {
                val scheduleSlot = scheduleSlots.firstOrNull { it.sessionId == id }

                if (scheduleSlot != null) {
                    Log.i("SessionAlarmService", scheduleSlot.toString())
                    val startTimestamp = format.parse(scheduleSlot.startDate).time
                    val endTimestamp = format.parse(scheduleSlot.endDate).time
                    scheduleAlarm(startTimestamp, endTimestamp,
                            scheduleSlot.sessionId, false)
                }
            }
        }
    }

    /**
     * Schedule an alarm for a given session that begins at a given time
     *
     * @param sessionStart    start time of the slot. The alarm will be fired before this time
     * @param sessionEnd      end time of the slot
     * @param sessionId       id of the session
     * @param allowLastMinute allow or not the alarm to be set if the delay between the alarm and
     * the session start is over.
     */
    private fun scheduleAlarm(sessionStart: Long,
                              sessionEnd: Long,
                              sessionId: String,
                              allowLastMinute: Boolean) {
        val currentTime = System.currentTimeMillis()

        Log.i("Time", "current: $currentTime, session start: $sessionStart")

        val alarmTime = sessionStart - FIVE_MINUTES_IN_MILLISECONDS

        if (allowLastMinute) {
            // If the session is already started, do not schedule system notification.
            if (currentTime > sessionStart) {
                logDebug("Not scheduling alarm because target time is in the past: $sessionStart")
                return
            }
        } else {
            if (currentTime > alarmTime) {
                logDebug("Not scheduling alarm because alarm time is in the past: $alarmTime")
                return
            }
        }

        AndroidMakersStore().getSession(sessionId) { session ->
            AndroidMakersStore().getSlotsFlow { slots ->
                val slotToNotify = slots.firstOrNull { it.sessionId == sessionId }
                if (session == null || slotToNotify == null) {
                    Log.w(TAG, "Cannot find session $sessionId either in sessions or in slots")
                    return@getSlotsFlow
                }

                val startTimestamp = format.parse(slotToNotify.startDate).time
                val endTimestamp = format.parse(slotToNotify.endDate).time

                val sessionDate = DateUtils.formatDateRange(this,
                        Formatter(Locale.getDefault()),
                        startTimestamp,
                        endTimestamp,
                        DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_ABBREV_WEEKDAY or DateUtils.FORMAT_SHOW_TIME, null).toString()

                AndroidMakersStore().getRoom(slotToNotify.roomId) { room ->
                    val roomName = (if (room != null) room.roomName else "") + " - "

                    logDebug("Scheduling alarm for " + alarmTime + " = " + Date(alarmTime).toString())

                    val notificationIntent = Intent(
                            ACTION_NOTIFY_SESSION,
                            Uri.parse(sessionId),
                            this,
                            SessionAlarmReceiver::class.java)
                    notificationIntent.putExtra(EXTRA_SESSION_START, sessionStart)
                    logDebug("-> Intent extra: session start $sessionStart")
                    notificationIntent.putExtra(EXTRA_SESSION_END, sessionEnd)
                    logDebug("-> Intent extra: session end $sessionEnd")
                    notificationIntent.putExtra(EXTRA_SESSION_ID, sessionId)
                    notificationIntent.putExtra(EXTRA_NOTIFICATION_TITLE, session.title)
                    notificationIntent.putExtra(EXTRA_NOTIFICATION_CONTENT, roomName + sessionDate)

                    val pendingIntent = PendingIntent.getBroadcast(this, sessionId.hashCode(), notificationIntent, 0)

                    val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    // Schedule an alarm to be fired to notify user of added sessions are about to begin.
                    logDebug("-> Scheduling RTC_WAKEUP alarm at $alarmTime")
                    AlarmManagerCompat.setExact(alarmManager, AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
                }
            }
        }
    }

    /**
     * Remove the scheduled alarm for a given session
     *
     * @param sessionId the id of the session
     */
    private fun unscheduleAlarm(sessionId: String) {
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val notifIntent = Intent(
                ACTION_NOTIFY_SESSION,
                Uri.parse(sessionId),
                this,
                SessionAlarmReceiver::class.java)
        val pi = PendingIntent.getBroadcast(this, sessionId.hashCode(), notifIntent, 0)

        am.cancel(pi)
    }

    // Starred sessions are about to begin. Constructs and triggers system notification.
    private fun notifySession(sessionId: String,
                              notificationTitle: String,
                              notificationContent: String) {
        // Generates the pending intent which gets fired when the user taps on the notification.
        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(
                this,
                0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channelId = "Sessions"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setColor(resources.getColor(R.color.colorPrimary))
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                .setLights(
                        SessionAlarmService.NOTIFICATION_ARGB_COLOR,
                        SessionAlarmService.NOTIFICATION_LED_ON_MS,
                        SessionAlarmService.NOTIFICATION_LED_OFF_MS)
                .setSmallIcon(R.drawable.ic_event_note_white_24dp)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setAutoCancel(true)

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(channelId,
                    "Sessions about to begin",
                    NotificationManager.IMPORTANCE_HIGH)
            channel.setShowBadge(false)
            channel.enableLights(true)
            channel.lightColor = SessionAlarmService.NOTIFICATION_ARGB_COLOR
            channel.enableVibration(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(channel)
        }

        logDebug("Now showing notification.")
        NotificationManagerCompat.from(this).notify(sessionId.hashCode(), notificationBuilder.build())
    }

    companion object {
        /**
         * Unique job ID for this service.
         */
        private val JOB_ID = 1000
        private val TAG = "sessionAlarm"

        val ACTION_NOTIFY_SESSION = "NOTIFY_SESSION"

        val ACTION_SCHEDULE_STARRED_BLOCK = "SCHEDULE_STARRED_BLOCK"
        val ACTION_UNSCHEDULE_UNSTARRED_BLOCK = "ACTION_UNSCHEDULE_UNSTARRED_BLOCK"
        val ACTION_SCHEDULE_ALL_STARRED_BLOCKS = "SCHEDULE_ALL_STARRED_BLOCKS"

        val EXTRA_SESSION_ID = "SESSION_ID"
        val EXTRA_SESSION_START = "SESSION_START"
        val EXTRA_SESSION_END = "SESSION_END"
        val EXTRA_NOTIFICATION_TITLE = "NOTIF_TITLE"
        val EXTRA_NOTIFICATION_CONTENT = "NOTIF_CONTENT"

        private val NOTIFICATION_LED_ON_MS = 100
        private val NOTIFICATION_LED_OFF_MS = 1000
        private val NOTIFICATION_ARGB_COLOR = -0xe1491f

        private val FIVE_MINUTES_IN_MILLISECONDS: Long = 300000

        private val UNDEFINED_VALUE: Long = -1

        /**
         * Convenience method for enqueuing work in to this service.
         */
        fun enqueueWork(context: Context, work: Intent) {
            JobIntentService.enqueueWork(context, SessionAlarmService::class.java, JOB_ID, work)
        }

        fun logDebug(message: String) {
            Log.d(TAG, message)
        }
    }

}
