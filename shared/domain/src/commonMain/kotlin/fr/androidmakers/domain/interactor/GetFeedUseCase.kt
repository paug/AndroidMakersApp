package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.LocationInfo
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFeedUseCase(
  private val feedRepository: FeedRepository,
) {
  operator fun invoke(): Flow<Result<List<FeedItem>>> = feedRepository.getFeedItems().map { result ->
    result.map { repoItems -> repoItems + hardcodedItems }
  }
}

private val hardcodedItems = listOf(
  FeedItem.Article(
    id = "article-1",
    category = "KEYNOTE",
    timeAgo = "2h ago",
    title = "Opening Keynote: Thriving in an AI era \uD83D\uDD2E",
    description = "Feeling stuck or unsure about your future as an Android developer? You’re not alone. Between GenAI and Agents getting better and better, sloth in hiring; the role of an Android engineer is shifting fast and it’s easy to feel “Am I still relevant? Should I be learning AI? Will Android still be Android in 5 years?”",
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
      name = "Café Oz Denfert",
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
