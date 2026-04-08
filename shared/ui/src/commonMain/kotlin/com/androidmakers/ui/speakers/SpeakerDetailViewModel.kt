package com.androidmakers.ui.speakers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.toLce
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.utils.UrlOpener
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class SpeakerDetailsViewModel(
  speakerId: String,
  speakersRepository: SpeakersRepository,
  sessionsRepository: SessionsRepository,
) : ViewModel() {

  val uiState: StateFlow<Lce<SpeakerDetailsUiState>> = combine(
    speakersRepository.getSpeaker(speakerId),
    sessionsRepository.getSessions(refresh = false),
  ) { speakerResult, sessionsResult ->
    speakerResult.map { speaker ->
      SpeakerDetailsUiState(
        speaker = speaker,
        sessions = sessionsResult.getOrDefault(emptyList())
          .filter { session -> speakerId in session.speakers }
      )
    }.toLce()
  }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = Lce.Loading
    )

  fun openSpeakerLink(urlOpener: UrlOpener, socialsItem: SocialsItem) {
    socialsItem.url?.let { urlOpener.openUrl(it) }
  }
}

data class SpeakerDetailsUiState(
  val speaker: Speaker,
  val sessions: List<Session> = emptyList(),
)
