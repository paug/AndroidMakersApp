package fr.androidmakers.store.graphql

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.MessageType
import fr.androidmakers.store.graphql.fragment.FeedMessageDetails
import fr.androidmakers.store.graphql.type.FeedItemType

fun FeedMessageDetails.toFeedItem(): FeedItem {
  return when (type) {
    FeedItemType.ALERT -> FeedItem.Alert(
      id = id,
      title = title,
      message = body,
    )
    else -> FeedItem.Message(
      id = id,
      type = when (type) {
        FeedItemType.ANNOUNCEMENT -> MessageType.ANNOUNCEMENT
        else -> MessageType.INFO
      },
      title = title,
      body = body,
      createdAt = createdAt,
    )
  }
}
