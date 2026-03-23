package fr.androidmakers.store.mock

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.MessageType
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.days

class MockFeedRepository : FeedRepository {
  override fun getFeedItems(): Flow<Result<List<FeedItem>>> {
    val now = Clock.System.now()
    return flowOf(
      Result.success(
        listOf(
          FeedItem.Alert(
            id = "alert-1",
            title = "Room Change Alert",
            message = "The Kotlin Workshop has moved from Room 3 to Room 7. Please update your schedule accordingly.",
          ),
          FeedItem.Message(
            id = "message-1",
            type = MessageType.ANNOUNCEMENT,
            title = "Opening Keynote: The Future of Android Development",
            body = "Join us for an exciting keynote session exploring the latest " +
              "innovations in Android development, from Compose Multiplatform to AI-powered tools.",
            createdAt = now - 2.hours,
          ),
          FeedItem.Message(
            id = "message-2",
            type = MessageType.INFO,
            title = "After-Hours Party",
            body = "Don't miss tonight's networking event with drinks and live music.",
            createdAt = now - 5.hours,
          ),
          FeedItem.Message(
            id = "message-3",
            type = MessageType.ANNOUNCEMENT,
            title = "Swag Alert: Limited Edition T-Shirts",
            body = "Pick up your exclusive Android Makers t-shirt at the registration desk. " +
              "Available while supplies last!",
            createdAt = now - 1.days,
          ),
        )
      )
    )
  }
}
