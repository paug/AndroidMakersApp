package fr.paug.androidmakers.wear.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalPreferencesRepository(
  private val dataStore: DataStore<Preferences>
) {
  val showOnlyBookmarkedSessions: Flow<Boolean> = dataStore.data.map { prefs ->
    prefs[PREF_KEY_SHOW_ONLY_BOOKMARK_SESSIONS] == true
  }

  suspend fun setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions: Boolean) {
    dataStore.edit { prefs ->
      prefs[PREF_KEY_SHOW_ONLY_BOOKMARK_SESSIONS] = showOnlyBookmarkedSessions
    }
  }

  companion object {
    private val PREF_KEY_SHOW_ONLY_BOOKMARK_SESSIONS = booleanPreferencesKey("show_only_bookmark_sessions")
  }
}
