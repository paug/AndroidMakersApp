package fr.androidmakers.store.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import fr.androidmakers.domain.model.ThemePreference
import fr.androidmakers.domain.repo.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeDataStoreRepository(
  private val dataStore: DataStore<Preferences>
) : ThemeRepository {

  override val themePreference: Flow<ThemePreference> =
    dataStore.data.map { prefs ->
      val name = prefs[PREF_KEY_THEME]
      ThemePreference.entries.firstOrNull { it.name == name } ?: ThemePreference.System
    }

  override suspend fun setThemePreference(preference: ThemePreference) {
    dataStore.edit { prefs ->
      prefs[PREF_KEY_THEME] = preference.name
    }
  }

  override val showDebugInfo: Flow<Boolean> =
    dataStore.data.map { prefs ->
      prefs[PREF_KEY_SHOW_DEBUG_INFO] ?: false
    }

  override suspend fun setShowDebugInfo(show: Boolean) {
    dataStore.edit { prefs ->
      prefs[PREF_KEY_SHOW_DEBUG_INFO] = show
    }
  }

  companion object {
    private val PREF_KEY_THEME = stringPreferencesKey("theme_preference")
    private val PREF_KEY_SHOW_DEBUG_INFO = booleanPreferencesKey("show_debug_info")
  }
}
