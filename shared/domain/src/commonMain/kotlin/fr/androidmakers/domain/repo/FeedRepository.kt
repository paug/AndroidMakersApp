package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.FeedItem
import kotlinx.coroutines.flow.Flow

interface FeedRepository {
  fun getFeedItems(): Flow<Result<List<FeedItem>>>
  suspend fun addFeedItem(item: FeedItem)
}
