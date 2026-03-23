package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.scan

class FeedGraphQLRepository(
  private val apolloClient: ApolloClient,
) : FeedRepository {

  override fun getFeedItems(): Flow<Result<List<FeedItem>>> {
    val initialItems = apolloClient.query(GetFeedMessagesQuery())
      .cacheAndNetwork()
      .map { result -> result.map { data -> data.feedMessages.map { it.feedMessageDetails.toFeedItem() } } }

    val newItems = apolloClient.subscription(OnFeedMessageAddedSubscription())
      .toFlow()
      .mapNotNull { response ->
        response.data?.let { data ->
          Result.success(listOf(data.feedMessageAdded.feedMessageDetails.toFeedItem()))
        }
      }
      .catch { /* subscription error — silently stop, initial data still shown */ }

    return merge(initialItems, newItems)
      .scan<Result<List<FeedItem>>, Result<List<FeedItem>>>(Result.success(emptyList())) { acc, result ->
        val newData = result.getOrNull()
        if (newData == null) {
          // Propagate error only if we have no data yet
          if (acc.getOrNull().isNullOrEmpty()) result else acc
        } else {
          val existing = acc.getOrNull().orEmpty()
          val existingIds = existing.map { it.id }.toSet()
          val unique = newData.filter { it.id !in existingIds }
          Result.success(existing + unique)
        }
      }
  }
}
