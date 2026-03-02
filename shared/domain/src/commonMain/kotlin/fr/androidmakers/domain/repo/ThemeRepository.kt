package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.ThemePreference
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {
  val themePreference: Flow<ThemePreference>
  suspend fun setThemePreference(preference: ThemePreference)
}
