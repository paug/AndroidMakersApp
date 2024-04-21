package fr.androidmakers.store.wear

import fr.androidmakers.domain.repo.MessagingRepository

class WearMessagingRepository(private val wearMessaging: WearMessaging) : MessagingRepository {
  override suspend fun sendSyncBookmarksMessage() {
    wearMessaging.sendSyncBookmarksMessage()
  }
}
