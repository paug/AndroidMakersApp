package fr.paug.androidmakers.wear.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import kotlinx.coroutines.launch

class SettingsViewModel(
  application: Application,
  private val localPreferencesRepository: LocalPreferencesRepository,
) : AndroidViewModel(application) {

  val showOnlyBookmarkedSessions = localPreferencesRepository.showOnlyBookmarkedSessions

  fun setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions: Boolean) {
    viewModelScope.launch {
      localPreferencesRepository.setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions)
    }
  }
}
