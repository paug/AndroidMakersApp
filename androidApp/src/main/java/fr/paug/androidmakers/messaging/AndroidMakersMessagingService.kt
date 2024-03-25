package fr.paug.androidmakers.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import fr.paug.androidmakers.MainActivity
import fr.paug.androidmakers.R

class AndroidMakersMessagingService : FirebaseMessagingService() {

  override fun onMessageReceived(remoteMessage: RemoteMessage) {

    Log.d(TAG, "From: ${remoteMessage.from}")

    // Check if message contains a data payload.
    if (remoteMessage.data.isNotEmpty()) {
      Log.d(TAG, "Message data payload: ${remoteMessage.data}")

      // Check if message contains a notification payload.
      remoteMessage.notification?.let {
        Log.d(TAG, "Message Notification Body: ${it.body}")

      }


      // Also if you intend on generating your own notifications as a result of a received FCM
      // message, here is where that should be initiated. See sendNotification method below.

    }

    remoteMessage.notification?.body?.let {
      sendNotification(it)
    }
  }


  private fun sendNotification(messageBody: String) {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(
      this, 0 /* Request code */, intent,
      PendingIntent.FLAG_IMMUTABLE
    )

    val channelId = "fcm_default_channel"
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)
      .setContentTitle("FCM Message")
      .setContentText(messageBody)
      .setSmallIcon(R.drawable.ic_notification_small)
      .setAutoCancel(true)
      .setSound(defaultSoundUri)
      .setContentIntent(pendingIntent)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Since android Oreo notification channel is needed.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        channelId,
        "Channel human readable title",
        NotificationManager.IMPORTANCE_DEFAULT
      )
      notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
  }


  companion object {
    const val TAG = "MessagingService"
  }

}
