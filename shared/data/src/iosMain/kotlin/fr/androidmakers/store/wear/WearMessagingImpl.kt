package fr.androidmakers.store.wear

class WearMessagingImpl : WearMessaging {
  override suspend fun sendSyncBookmarksMessage() {
    // No-op on iOS
  }
}
