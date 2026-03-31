package fr.androidmakers.store.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.MessageType
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FeedLocalRepository(
  private val dataStore: DataStore<Preferences>,
) : FeedRepository {

  private val json = Json { ignoreUnknownKeys = true }

  override fun getFeedItems(): Flow<Result<List<FeedItem>>> {
    return dataStore.data.map { prefs ->
      val raw = prefs[PREF_KEY_FEED_ITEMS] ?: "[]"
      val stored = runCatching { json.decodeFromString<List<StoredFeedItem>>(raw) }
        .getOrDefault(emptyList())
      Result.success(stored.sortedByDescending { it.createdAtEpochMillis }.map { it.toFeedItem() })
    }
  }

  override suspend fun addFeedItem(item: FeedItem) {
    val message = item as? FeedItem.Message ?: return
    val stored = StoredFeedItem(
      id = message.id,
      type = message.type.name,
      title = message.title,
      body = message.body,
      createdAtEpochMillis = message.createdAt.toEpochMilliseconds(),
    )
    dataStore.edit { prefs ->
      val raw = prefs[PREF_KEY_FEED_ITEMS] ?: "[]"
      val existing = runCatching { json.decodeFromString<List<StoredFeedItem>>(raw) }
        .getOrElse {
          prefs.remove(PREF_KEY_FEED_ITEMS)
          emptyList()
        }
        .toMutableList()
      if (existing.none { it.id == stored.id }) {
        existing.add(stored)
      }
      val trimmed = existing.sortedByDescending { it.createdAtEpochMillis }.take(MAX_ITEMS)
      prefs[PREF_KEY_FEED_ITEMS] = json.encodeToString(trimmed)
    }
  }

  companion object {
    private val PREF_KEY_FEED_ITEMS = stringPreferencesKey("feed_items")
    private const val MAX_ITEMS = 100
  }
}

@Serializable
private data class StoredFeedItem(
  val id: String,
  val type: String,
  val title: String,
  val body: String,
  val createdAtEpochMillis: Long,
) {
  fun toFeedItem(): FeedItem.Message = FeedItem.Message(
    id = id,
    type = MessageType.entries.firstOrNull { it.name == type } ?: MessageType.INFO,
    title = title,
    body = body,
    createdAt = Instant.fromEpochMilliseconds(createdAtEpochMillis),
  )
}
