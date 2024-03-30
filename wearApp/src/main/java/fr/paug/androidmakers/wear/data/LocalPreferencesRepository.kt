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

  companion object {
    private const val PREF_SHOW_ONLY_BOOKMARK_SESSIONS = "show_only_bookmark_sessions"
  }

  val showOnlyBookmarkedSessions: Flow<Boolean> = dataStore.data.map { prefs ->
    prefs[booleanPreferencesKey(PREF_SHOW_ONLY_BOOKMARK_SESSIONS)] ?: false
  }

  suspend fun setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions: Boolean) {
    dataStore.edit { prefs ->
      prefs[booleanPreferencesKey(PREF_SHOW_ONLY_BOOKMARK_SESSIONS)] = showOnlyBookmarkedSessions
    }
  }
}
