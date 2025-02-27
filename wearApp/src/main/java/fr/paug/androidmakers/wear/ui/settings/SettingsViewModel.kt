package fr.paug.androidmakers.wear.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel(
  private val localPreferencesRepository: LocalPreferencesRepository,
) : ViewModel() {

  val showOnlyBookmarkedSessions: Flow<Boolean> = localPreferencesRepository.showOnlyBookmarkedSessions

  fun setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions: Boolean) {
    viewModelScope.launch {
      localPreferencesRepository.setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions)
    }
  }
}
