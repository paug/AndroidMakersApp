package com.androidmakers.utils

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
import fr.androidmakers.domain.model.Session
import fr.paug.androidmakers.ui.MR
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.StringResource
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

actual class NotificationUtils(private val context: Context) {

    actual fun initNotifications() {
        createChannel(NotificationChannels.GENERAL)
        createChannel(NotificationChannels.TALKS)
    }

    actual fun triggerNotification(session: UISession) {

        val channelId = NotificationChannels.TALKS.id
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setContentTitle(session.title)
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
    }

    actual fun scheduleNotification(session: UISession) {
        // TODO TEst
        val session = Session(
            id = "928928",
            title = "My session",
            roomId = "344",
            startsAt = (Clock.System.now() + 30.seconds).toLocalDateTime(TimeZone.currentSystemDefault()),
            endsAt =  (Clock.System.now() + 60.seconds).toLocalDateTime(TimeZone.currentSystemDefault()),
            isServiceSession = false
        ).toUISession(rooms = emptyMap(), speakers = emptyMap(), isFavorite = false)

        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val intent = Intent(context, SessionAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Random.nextInt(),
            intent,
            0
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            5_000,
            pendingIntent
        )
    }
}

private fun NotificationChannels.displayName(context: Context) = when (this) {
    NotificationChannels.TALKS -> MR.strings.notification_channel_talks.getString(context)
    NotificationChannels.GENERAL -> MR.strings.notification_channel_general.getString(context)
}
