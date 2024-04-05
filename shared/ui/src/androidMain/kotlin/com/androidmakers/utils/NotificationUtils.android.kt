package com.androidmakers.utils

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.model.Session
import fr.paug.androidmakers.ui.MR
import org.jetbrains.compose.resources.StringResource

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
}

private fun NotificationChannels.displayName(context: Context) = when (this) {
    NotificationChannels.TALKS -> MR.strings.notification_channel_talks.getString(context)
    NotificationChannels.GENERAL -> MR.strings.notification_channel_general.getString(context)
}
