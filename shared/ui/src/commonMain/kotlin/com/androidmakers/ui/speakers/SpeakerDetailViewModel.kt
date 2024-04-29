package com.androidmakers.ui.speakers

import com.androidmakers.ui.model.Lce
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.OpenLinkUseCase
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SpeakerDetailsViewModel(
    speakerId: String,
    speakersRepository: SpeakersRepository,
  private val openLinkUseCase: OpenLinkUseCase,
) : ViewModel() {

  val uiState: StateFlow<Lce<SpeakerDetailsUiState>> = speakersRepository
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

  fun openSpeakerLink(platformContext: PlatformContext, socialsItem: SocialsItem) {
    socialsItem.url?.let { openLinkUseCase(platformContext, it) }
  }
}

data class SpeakerDetailsUiState(
    val speaker: Speaker
)
