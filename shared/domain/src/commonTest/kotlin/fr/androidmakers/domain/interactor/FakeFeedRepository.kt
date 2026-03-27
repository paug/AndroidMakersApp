package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeFeedRepository : FeedRepository {
    val feedItemsFlow = MutableStateFlow<Result<List<FeedItem>>>(Result.success(emptyList()))

    override fun getFeedItems(): Flow<Result<List<FeedItem>>> = feedItemsFlow

    override suspend fun addFeedItem(item: FeedItem) {
        val current = feedItemsFlow.value.getOrDefault(emptyList())
        feedItemsFlow.value = Result.success(listOf(item) + current)
    }
}
