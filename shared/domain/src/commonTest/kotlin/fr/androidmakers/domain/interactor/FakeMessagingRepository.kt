package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.MessagingRepository

class FakeMessagingRepository : MessagingRepository {
    var syncMessageSent = false

    override suspend fun sendSyncBookmarksMessage() {
        syncMessageSent = true
    }
}
