package fr.paug.androidmakers.wear.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository

class SettingsViewModel(
    application: Application,
    private val localPreferencesRepository: LocalPreferencesRepository,
) : AndroidViewModel(application) {

  val showOnlyBookmarkedSessions = localPreferencesRepository.showOnlyBookmarkedSessions

  fun setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions: Boolean) {
    localPreferencesRepository.showOnlyBookmarkedSessions.value = showOnlyBookmarkedSessions
  }
}
