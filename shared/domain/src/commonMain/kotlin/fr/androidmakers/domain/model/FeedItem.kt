package fr.androidmakers.domain.model

sealed interface FeedItem {
  val id: String

  data class Alert(
    override val id: String,
    val title: String,
    val message: String,
  ) : FeedItem

  data class Article(
    override val id: String,
    val category: String,
    val timeAgo: String,
    val title: String,
    val description: String,
    val imageUrl: String? = null,
    val thumbnailUrl: String? = null,
    val categoryBadge: String? = null,
    val location: LocationInfo? = null,
    val avatarUrls: List<String> = emptyList(),
    val readMoreUrl: String? = null,
  ) : FeedItem
}

data class LocationInfo(
  val name: String,
  val time: String? = null,
)
