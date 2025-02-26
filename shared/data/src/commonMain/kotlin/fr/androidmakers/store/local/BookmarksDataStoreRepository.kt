package fr.androidmakers.store.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import fr.androidmakers.domain.repo.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarksDataStoreRepository(
  private val dataStore: DataStore<Preferences>
) : BookmarksRepository {

  private suspend fun update(transform: suspend (existing: Set<String>) -> Set<String>) {
    dataStore.edit { prefs ->
      prefs[PREF_KEY_SELECTED_SESSIONS] = transform(prefs[PREF_KEY_SELECTED_SESSIONS].orEmpty())
    }
  }

  override suspend fun setBookmarked(sessionId: String, bookmarked: Boolean) {
    if (bookmarked) {
      update { it + sessionId }
    } else {
      update { it - sessionId }
    }
  }

  override fun isBookmarked(id: String): Flow<Boolean> {
    return favoriteSessions.map { id in it }
  }

  /**
   * This is called after a successful sign-in or at startup to merge the remote bookmarks into
   * the local ones
   */
  override suspend fun merge(bookmarks: Set<String>) {
    update { it + bookmarks }
  }

  override suspend fun setBookmarks(bookmarks: Set<String>) {
    update { bookmarks }
  }

  override val favoriteSessions: Flow<Set<String>> =
    dataStore.data.map { it[PREF_KEY_SELECTED_SESSIONS].orEmpty() }

  companion object {
    private val PREF_KEY_SELECTED_SESSIONS = stringSetPreferencesKey("selected_sessions")
  }
}
