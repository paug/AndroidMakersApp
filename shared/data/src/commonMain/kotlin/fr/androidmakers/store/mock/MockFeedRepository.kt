package fr.androidmakers.store.mock

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class MockFeedRepository : FeedRepository {

  private val items = MutableStateFlow<List<FeedItem>>(emptyList())

  override fun getFeedItems(): Flow<Result<List<FeedItem>>> {
    return items.map { Result.success(it) }
  }

  override suspend fun addFeedItem(item: FeedItem) {
    items.value = listOf(item) + items.value
  }
}
