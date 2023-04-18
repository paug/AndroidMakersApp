package fr.paug.androidmakers.ui.components.speakers

import androidx.lifecycle.ViewModel
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.ui.viewmodel.Lce
import kotlinx.coroutines.flow.map

class SpeakerViewModel : ViewModel() {

  val uiState = AndroidMakersApplication.instance().store.getSpeakers().map {
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