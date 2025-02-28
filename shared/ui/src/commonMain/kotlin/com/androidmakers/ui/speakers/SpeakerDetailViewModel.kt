package com.androidmakers.ui.speakers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.toLce
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.OpenLinkUseCase
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SpeakerDetailsViewModel(
  speakerId: String,
  speakersRepository: SpeakersRepository,
  private val openLinkUseCase: OpenLinkUseCase,
) : ViewModel() {

  val uiState: StateFlow<Lce<SpeakerDetailsUiState>> = speakersRepository
    .getSpeaker(speakerId).map { result ->
      result.map {
        SpeakerDetailsUiState(
          speaker = it
        )
      }.toLce()
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.Eagerly,
      initialValue = Lce.Loading
    )

  fun openSpeakerLink(platformContext: PlatformContext, socialsItem: SocialsItem) {
    socialsItem.url?.let { openLinkUseCase(platformContext, it) }
  }
}

data class SpeakerDetailsUiState(
  val speaker: Speaker
)
