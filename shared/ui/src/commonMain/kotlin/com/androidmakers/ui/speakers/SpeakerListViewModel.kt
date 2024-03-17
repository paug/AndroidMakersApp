package com.androidmakers.ui.speakers

import com.androidmakers.ui.model.Lce
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.map
import moe.tlaster.precompose.viewmodel.ViewModel

class SpeakerListViewModel(
    speakersRepository: SpeakersRepository
) : ViewModel() {

  val uiState = speakersRepository.getSpeakers().map {
    val exception = it.exceptionOrNull()
    if (exception != null) {
      Lce.Error
    } else {
      Lce.Content(SpeakersUiState(
          speakers = it.getOrThrow()
      ))
    }
  }
}

data class SpeakersUiState(
    val speakers: List<Speaker> = emptyList()
)
