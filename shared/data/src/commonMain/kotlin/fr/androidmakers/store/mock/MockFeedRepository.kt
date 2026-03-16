package fr.androidmakers.store.mock

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.LocationInfo
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockFeedRepository : FeedRepository {
  override fun getFeedItems(): Flow<Result<List<FeedItem>>> {
    return flowOf(
      Result.success(
        listOf(
          FeedItem.Alert(
            id = "alert-1",
            title = "Room Change Alert",
            message = "The Kotlin Workshop has moved from Room 3 to Room 7. Please update your schedule accordingly.",
          ),
          FeedItem.Article(
            id = "article-1",
            category = "KEYNOTE",
            timeAgo = "2h ago",
            title = "Opening Keynote: The Future of Android Development",
            description = "Join us for an exciting keynote session exploring the latest " +
              "innovations in Android development, from Compose Multiplatform to AI-powered tools.",
            imageUrl = "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800",
            categoryBadge = "KEYNOTE",
            avatarUrls = listOf(
              "https://i.pravatar.cc/150?img=1",
              "https://i.pravatar.cc/150?img=2",
              "https://i.pravatar.cc/150?img=3",
            ),
            readMoreUrl = "https://androidmakers.droidcon.com",
          ),
          FeedItem.Article(
            id = "article-2",
            category = "EVENT",
            timeAgo = "5h ago",
            title = "After-Hours Party",
            description = "Don't miss tonight's networking event with drinks and live music.",
            location = LocationInfo(
              name = "Le Café des Makers",
              time = "7:00 PM - 11:00 PM",
            ),
          ),
          FeedItem.Article(
            id = "article-3",
            category = "ANNOUNCEMENT",
            timeAgo = "1d ago",
            title = "Swag Alert: Limited Edition T-Shirts",
            description = "Pick up your exclusive Android Makers t-shirt at the registration desk. " +
              "Available while supplies last!",
            thumbnailUrl = "https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=200",
          ),
        )
      )
    )
  }
}
