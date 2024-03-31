package fr.paug.androidmakers.wear.ui.session.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.paug.androidmakers.wear.ui.session.UISession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SessionDetailViewModel(
  sessionId: String,
  sessionsRepository: SessionsRepository,
  private val roomsRepository: RoomsRepository,
  bookmarksRepository: BookmarksRepository,
  private val speakersRepository: SpeakersRepository,
  private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
) : ViewModel() {

  private val session: Flow<Session> = sessionsRepository.getSession(sessionId)
    .filterSuccess()
    .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = null)
    .filterNotNull()

  private val room: Flow<Room> = session.map { session ->
    roomsRepository.getRoom(session.roomId)
      .filterSuccess()
      .first()
  }

  private val speakers: Flow<List<Speaker>> = session.map { session ->
    session.speakers.map {
      speakersRepository.getSpeaker(it).filterSuccess().first()
    }
  }

  private val isBookmarked: Flow<Boolean> = bookmarksRepository.isBookmarked(sessionId)

  val uiSession: Flow<UISession> = combine(
    session,
    room,
    speakers,
    isBookmarked,
  ) { session, room, speakers, isBookmarked ->
    UISession(
      session = session,
      speakers = speakers,
      room = room,
      isBookmarked = isBookmarked,
    )
  }

  fun bookmark(bookmarked: Boolean) = viewModelScope.launch {
    setSessionBookmarkUseCase(session.first().id, bookmarked)
  }
}

private fun <T> Flow<Result<T>>.filterSuccess(): Flow<T> =
  filter { it.isSuccess }.map { it.getOrThrow() }
