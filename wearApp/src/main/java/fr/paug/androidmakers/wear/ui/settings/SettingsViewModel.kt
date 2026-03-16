package fr.paug.androidmakers.wear.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
  private val localPreferencesRepository: LocalPreferencesRepository,
) : ViewModel() {

  val showOnlyBookmarkedSessions: StateFlow<Boolean> = localPreferencesRepository.showOnlyBookmarkedSessions
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = false
    )

  fun setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions: Boolean) {
    viewModelScope.launch {
      localPreferencesRepository.setShowOnlyBookmarkedSessions(showOnlyBookmarkedSessions)
    }
  }
}
