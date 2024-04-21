package fr.androidmakers.store.wear

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await

class WearMessagingImpl(context: Context) : WearMessaging {
  companion object {
    const val MESSAGE_SYNC_BOOKMARKS = "syncBookmarks"
  }

  private val nodeClient = Wearable.getNodeClient(context)
  private val messageClient = Wearable.getMessageClient(context)

  override suspend fun sendSyncBookmarksMessage() {
    try {
      val allNodeIds = nodeClient.connectedNodes.await().map { it.id }
      for (nodeId in allNodeIds) {
        messageClient.sendMessage(nodeId, MESSAGE_SYNC_BOOKMARKS, null).await()
      }
    } catch (t: Throwable) {
      Log.w("WearMessagingImpl", "Error sending message", t)
    }
  }
}
