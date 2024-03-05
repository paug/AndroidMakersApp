package fr.androidmakers.store.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import fr.androidmakers.domain.repo.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class BookmarksDataStoreRepository(
    private val dataStore: DataStore<Preferences>
) : BookmarksRepository {
  private var bookmarkedSessions: MutableSet<String> = HashSet()

  private var selectedSessionIds: MutableStateFlow<Set<String>>

  init {
    val prefSet = runBlocking {
      dataStore.data.map { prefs ->
        prefs[stringSetPreferencesKey(PREF_SELECTED_SESSIONS)]
      }.first()
    }
    if (prefSet != null) {
      bookmarkedSessions.addAll(prefSet)
    }

    selectedSessionIds = MutableStateFlow(
        mutableSetOf<String>().apply {
          addAll(bookmarkedSessions)
        }
    )
  }

  override suspend fun setBookmarked(sessionId: String, bookmarked: Boolean) {
    if (bookmarked) {
      bookmarkedSessions.add(sessionId)
    } else {
      bookmarkedSessions.remove(sessionId)
    }
    selectedSessionIds.value = mutableSetOf<String>().apply { addAll(bookmarkedSessions) }
    save()
  }

  override fun isBookmarked(id: String): Boolean {
    return bookmarkedSessions.contains(id)
  }

  override fun subscribe(id: String): Flow<Boolean> =
      dataStore.data.map { prefs ->
        val sessions = prefs[stringSetPreferencesKey(PREF_SELECTED_SESSIONS)]
        sessions?.contains(id) ?: false
      }

  private suspend fun save() {
    dataStore.edit { prefs ->
      prefs[stringSetPreferencesKey(PREF_SELECTED_SESSIONS)] = bookmarkedSessions
    }
  }

  /**
   * This is called after a successful signin or at startup to merge the remote bookmarks into
   * the local ones
   */
  override suspend fun merge(bookmarks: Set<String>) {
    bookmarkedSessions.addAll(bookmarks)

    selectedSessionIds.value = mutableSetOf<String>().apply { addAll(bookmarkedSessions) }
    save()
  }

  override fun getFavoriteSessions(): Flow<Set<String>> {
    return selectedSessionIds
  }

  companion object {
    private const val PREF_SELECTED_SESSIONS = "selected_sessions"
  }
}
