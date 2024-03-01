package fr.paug.androidmakers.ui.components.speakers.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.model.SpeakerId
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.ui.viewmodel.Lce
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SpeakerDetailsViewModel(
    speakerId: SpeakerId
) : ViewModel() {

  val uiState: StateFlow<Lce<SpeakerDetailsUiState>> = AndroidMakersApplication.instance().speakersRepository
      .getSpeaker(speakerId).map {
        val exception = it.exceptionOrNull()
        if (exception != null) {
          Lce.Error
        } else {
          Lce.Content(SpeakerDetailsUiState(
              speaker = it.getOrThrow()
          ))
        }
      }
      .stateIn(
          scope = viewModelScope,
          started = SharingStarted.WhileSubscribed(5000L),
          initialValue = Lce.Loading
      )
}

data class SpeakerDetailsUiState(
    val speaker: Speaker
)
