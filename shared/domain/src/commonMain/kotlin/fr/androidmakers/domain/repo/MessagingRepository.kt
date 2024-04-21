package fr.androidmakers.domain.repo

interface MessagingRepository {
  suspend fun sendSyncBookmarksMessage()
}
