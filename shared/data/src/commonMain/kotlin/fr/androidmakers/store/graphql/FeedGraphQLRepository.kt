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
    return apolloClient.query(GetFeedMessagesQuery())
      .cacheAndNetwork()
      .map { result -> result.map { data -> data.feedItemsConnection.nodes.map { it.feedMessageDetails.toFeedItem() } } }
  }
}
