package fr.androidmakers.di

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.MessageType
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.Foundation.NSUUID

class FeedHelper : KoinComponent {
  private val feedRepository: FeedRepository by inject()
  private val scope = MainScope()

  fun saveFeedItem(id: String?, type: String?, title: String, body: String) {
    val messageType = type?.let { typeName ->
      MessageType.entries.firstOrNull { it.name == typeName }
    } ?: MessageType.INFO

    val feedItem = FeedItem.Message(
      id = id ?: NSUUID().UUIDString(),
      type = messageType,
      title = title,
      body = body,
      createdAt = Instant.fromEpochMilliseconds((NSDate().timeIntervalSince1970 * 1000).toLong()),
    )
    scope.launch {
      feedRepository.addFeedItem(feedItem)
    }
  }

  fun cancel() {
    scope.cancel()
  }
}
