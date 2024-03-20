package fr.paug.androidmakers.ui.components.session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.model.Lce
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@OptIn(ExperimentalCoroutinesApi::class)
class SessionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    sessionsRepository: SessionsRepository,
    private val roomsRepository: RoomsRepository,
    // TODO remove the reference to repositories here
    bookmarksRepository: BookmarksRepository,
    private val speakersRepository: SpeakersRepository,
    private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
) : ViewModel() {

  private val sessionId: String = savedStateHandle["sessionId"]!!
  private val session = sessionsRepository.getSession(sessionId)
  private val room = session.mapNotNull { result ->
    result.getOrNull()?.roomId?.let { roomId ->
      roomsRepository.getRoom(roomId)
    }
  }.flattenMerge()

  private val isBookmarked = bookmarksRepository.isBookmarked(sessionId)

  val sessionDetailState = combine(
      session,
      room,
      isBookmarked,
  ) { session, room, isBookmarked ->

    val exception = session.exceptionOrNull() ?: room.exceptionOrNull()
    if (exception != null) {
      Lce.Error
    } else {
      Lce.Content(
          SessionDetailState(
              session = session.getOrThrow(),
              room = room.getOrThrow(),
              speakers = getSpeakers(session.getOrThrow()),
              startTimestamp = session.getOrThrow().startsAt.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
              endTimestamp = session.getOrThrow().endsAt.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds(),
              isBookmarked = isBookmarked,
          )
      )
    }
  }

  private suspend fun getSpeakers(session: Session): List<Speaker> {
    val allSpeakers = speakersRepository.getSpeakers().firstOrNull()
        ?.recover { emptyList() }
        ?.getOrThrow()
        ?: return emptyList()

    val allSpeakersById = allSpeakers.associateBy { it.id }
    return session.speakers.mapNotNull { allSpeakersById[it] }
  }

  fun bookmark(bookmarked: Boolean) = viewModelScope.launch {
    setSessionBookmarkUseCase(session.first().getOrThrow().id, bookmarked)
  }
}
