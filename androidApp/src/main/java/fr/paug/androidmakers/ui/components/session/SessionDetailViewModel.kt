package fr.paug.androidmakers.ui.components.session

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.paug.androidmakers.ui.viewmodel.Lce
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@OptIn(ExperimentalCoroutinesApi::class)
class SessionDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val sessionsRepository: SessionsRepository,
    private val roomsRepository: RoomsRepository,
    private val bookmarksRepository: BookmarksRepository,
    private val speakersRepository: SpeakersRepository
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
        //?.recover { emptyList() }
        ?.getOrThrow()
        ?: return emptyList()

    val allSpeakersById = allSpeakers.associateBy { it.id }
    return session.speakers.mapNotNull { allSpeakersById[it] }
  }

  fun bookmark(bookmarked: Boolean) = viewModelScope.launch {
    bookmarksRepository.setBookmarked(sessionId, bookmarked)

    val userId = FirebaseAuth.getInstance().currentUser?.uid
    if (userId != null) {
      GlobalScope.launch {
        sessionsRepository.setBookmark(userId, sessionId, bookmarked)
      }
    }
  }
}
