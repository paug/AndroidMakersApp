package fr.paug.androidmakers.ui.components.speakers

import androidx.lifecycle.ViewModel
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.ui.viewmodel.Lce
import kotlinx.coroutines.flow.map

class SpeakerViewModel(
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
