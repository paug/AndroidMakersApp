package com.androidmakers.ui.speakers

import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.toLce
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import moe.tlaster.precompose.viewmodel.ViewModel

class SpeakerListViewModel(
    speakersRepository: SpeakersRepository
) : ViewModel() {

  val uiState: Flow<Lce<SpeakersUiState>> = speakersRepository.getSpeakers().map { result ->
    result.map {
      SpeakersUiState(
        speakers = it
      )
    }.toLce()
  }
}

data class SpeakersUiState(
    val speakers: List<Speaker> = emptyList()
)
