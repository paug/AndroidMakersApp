package com.androidmakers.ui.speakers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.toLce
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SpeakerListViewModel(
    speakersRepository: SpeakersRepository
) : ViewModel() {

  val uiState: StateFlow<Lce<SpeakersUiState>> = speakersRepository.getSpeakers(false)
    .map { result ->
      result.map {
        SpeakersUiState(
          speakers = it
        )
      }.toLce()
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = Lce.Loading
    )
}

data class SpeakersUiState(
    val speakers: List<Speaker> = emptyList()
)
