package com.androidmakers.utils

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_CANCEL_CURRENT
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.AlarmManagerCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.androidmakers.ui.agenda.toUISession
import com.androidmakers.ui.model.UISession
import com.androidmakers.utils.SessionAlarmReceiver.Companion.SESSION_ID
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.utils.formatShortTime
import fr.paug.androidmakers.ui.MR
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import kotlin.random.Random
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

actual class NotificationUtils(private val context: Context) {

    actual fun initNotifications() {
        createChannel(NotificationChannels.GENERAL)
        createChannel(NotificationChannels.TALKS)
    }

    actual fun triggerNotification(session: Session) {

        val channelId = NotificationChannels.TALKS.id
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(session.title)
            .setContentText(
                StringDesc.ResourceFormatted(
                    MR.strings.notification_upcoming_talk,
                    session.startsAt.formatShortTime(),
                    "Salle Mobo"
                ).toString(context)
            )
            .setSmallIcon(MR.images.ic_notification_small.drawableResId)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(session.notificationId(), notificationBuilder.build())
    }

    private fun createChannel(channelInfo: NotificationChannels) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelInfo.id,
                channelInfo.displayName(context),
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationManager.createNotificationChannel(channel)
        }
    }

    actual fun cancelNotification(session: UISession) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            session.notificationId(),
            Intent(context, SessionAlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    actual fun scheduleNotification(session: UISession) {

        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(context, SessionAlarmReceiver::class.java).apply {
            putExtra(SESSION_ID, session.id)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            session.notificationId(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE
        )

        val debugSession = session.copy(startDate = Clock.System.now() + 15.minutes + 5.seconds)

        val notificationTime = debugSession.scheduledNotificationDate()
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            notificationTime.toEpochMilliseconds(),
            pendingIntent
        )
    }
}

private fun NotificationChannels.displayName(context: Context) = when (this) {
    NotificationChannels.TALKS -> MR.strings.notification_channel_talks.getString(context)
    NotificationChannels.GENERAL -> MR.strings.notification_channel_general.getString(context)
}
