package com.androidmakers.ui.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.SessionDetailState
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.utils.UrlOpener
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@OptIn(ExperimentalCoroutinesApi::class)
class SessionDetailViewModel(
    sessionId: String,
    sessionsRepository: SessionsRepository,
    private val roomsRepository: RoomsRepository,
    bookmarksRepository: BookmarksRepository,
    private val speakersRepository: SpeakersRepository,
    private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
    private val shareSessionUseCase: ShareSessionUseCase,
    private val applyForAppClinicUseCase: ApplyForAppClinicUseCase,
) : ViewModel() {

  val sessionDetailState: StateFlow<Lce<SessionDetailState>> = sessionsRepository.getSession(sessionId)
    .transformLatest { sessionResult ->
      sessionResult
        .onSuccess { session ->
          combine(
            roomsRepository.getRoom(session.roomId),
            speakersRepository.getSpeakers(session.speakers),
            bookmarksRepository.isBookmarked(sessionId)
          ) { roomResult, speakersResult, isBookmarked ->
            val room = roomResult.getOrElse { return@combine Lce.Error }
            val speakers = speakersResult.getOrElse { return@combine Lce.Error }
            Lce.Content(
              SessionDetailState(
                session = session,
                room = room,
                speakers = speakers,
                startTimestamp = session.startsAt.toInstant(TimeZone.currentSystemDefault()),
                endTimestamp = session.endsAt.toInstant(TimeZone.currentSystemDefault()),
                isBookmarked = isBookmarked,
              )
            )
          }.collect(this)
        }
        .onFailure {
          emit(Lce.Error)
        }
    }.stateIn(
      scope = viewModelScope,
      started = SharingStarted.Eagerly,
      initialValue = Lce.Loading
    )

  fun bookmark(bookmarked: Boolean) = viewModelScope.launch {
    val lce = sessionDetailState.first { it !is Lce.Loading }
    if (lce is Lce.Content) {
      setSessionBookmarkUseCase(lce.content.session.id, bookmarked)
    }
  }

  fun shareSession(context: PlatformContext) = viewModelScope.launch {
    val lce = sessionDetailState.first { it !is Lce.Loading }
    if (lce is Lce.Content) {
      val state = lce.content
      shareSessionUseCase(
        context = context,
        session = state.session,
        speakers = state.speakers,
        formattedDateAndRoom = state.formattedDateAndRoom
      )
    }
  }

  fun openLink(urlOpener: UrlOpener, socialsItem: SocialsItem) {
    socialsItem.url?.let { urlOpener.openUrl(it) }
  }

  fun applyForAppClinic(urlOpener: UrlOpener) {
    applyForAppClinicUseCase(urlOpener)
  }
}
