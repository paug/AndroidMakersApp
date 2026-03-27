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
import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.MessageType
import fr.androidmakers.domain.repo.FeedRepository
import fr.paug.androidmakers.MainActivity
import fr.paug.androidmakers.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import org.koin.android.ext.android.inject
import java.util.UUID

class AndroidMakersMessagingService : FirebaseMessagingService() {

  private val feedRepository: FeedRepository by inject()
  private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

  override fun onDestroy() {
    super.onDestroy()
    serviceScope.cancel()
  }

  override fun onNewToken(token: String) {
    Log.d(TAG, "Refreshed token: $token")
  }

  override fun onMessageReceived(remoteMessage: RemoteMessage) {
    Log.d(TAG, "From: ${remoteMessage.from}")

    val data = remoteMessage.data
    if (data.isNotEmpty()) {
      Log.d(TAG, "Message data payload: $data")
      saveFeedItem(data)
    }

    val title = data["feed_title"] ?: remoteMessage.notification?.title ?: "Android Makers"
    val body = data["feed_body"] ?: remoteMessage.notification?.body
    if (body != null) {
      sendNotification(title, body)
    }
  }

  private fun saveFeedItem(data: Map<String, String>) {
    val title = data["feed_title"] ?: return
    val body = data["feed_body"] ?: return
    val id = data["feed_id"] ?: UUID.randomUUID().toString()
    val type = data["feed_type"]?.let { typeName ->
      MessageType.entries.firstOrNull { it.name == typeName }
    } ?: MessageType.INFO

    val feedItem = FeedItem.Message(
      id = id,
      type = type,
      title = title,
      body = body,
      createdAt = Instant.fromEpochMilliseconds(System.currentTimeMillis()),
    )
    serviceScope.launch {
      feedRepository.addFeedItem(feedItem)
    }
  }

  private fun sendNotification(title: String, messageBody: String) {
    val intent = Intent(this, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    val pendingIntent = PendingIntent.getActivity(
      this, 0, intent,
      PendingIntent.FLAG_IMMUTABLE
    )

    val channelId = "fcm_default_channel"
    val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val notificationBuilder = NotificationCompat.Builder(this, channelId)
      .setContentTitle(title)
      .setContentText(messageBody)
      .setSmallIcon(R.drawable.ic_notification_small)
      .setAutoCancel(true)
      .setSound(defaultSoundUri)
      .setContentIntent(pendingIntent)

    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        channelId,
        "Android Makers",
        NotificationManager.IMPORTANCE_DEFAULT
      )
      notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(messageBody.hashCode(), notificationBuilder.build())
  }

  companion object {
    const val TAG = "MessagingService"
  }
}
