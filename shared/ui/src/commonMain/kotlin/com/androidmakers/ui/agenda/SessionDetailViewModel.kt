package com.androidmakers.ui.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.SessionDetailState
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.OpenLinkUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.utils.formatTimeInterval
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
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
    private val openLinkUseCase: OpenLinkUseCase,
    private val applyForAppClinicUseCase: ApplyForAppClinicUseCase,
) : ViewModel() {

  private val session: Flow<Result<Session>> = sessionsRepository.getSession(sessionId)
  private val room: Flow<Result<Room>> = session.flatMapConcat{ result ->
    result.fold(
      onSuccess = { roomsRepository.getRoom(it.roomId) },
      onFailure = { flowOf(Result.failure(it)) }
    )
  }

  private val speakers: Flow<List<Speaker>> = session.mapNotNull { result ->
    result.getOrNull()?.speakers?.let { speakerIds ->
      speakersRepository.getSpeakers(speakerIds).first().getOrNull()
    }
  }

  private val formattedDateAndRoom: Flow<String> =
    combine(session, room) { sessionResult, roomResult ->
      sessionResult.fold(
        onSuccess = {
          val sessionDate = formatTimeInterval(
            it.startsAt,
            it.endsAt
          )
          val roomName = roomResult.getOrNull()?.name
          if (!roomName.isNullOrEmpty()) {
            "$sessionDate, $roomName"
          } else {
            sessionDate
          }
        },
        onFailure = { "" }
      )
    }

  val sessionDetailState: Flow<Lce<SessionDetailState>> = combine(
    session,
    room,
    bookmarksRepository.isBookmarked(sessionId),
  ) { sessionResult, roomResult, isBookmarked ->

    val session = sessionResult.getOrElse {
      return@combine Lce.Error
    }
    val room = roomResult.getOrElse {
      return@combine Lce.Error
    }
    Lce.Content(
      SessionDetailState(
        session = session,
        room = room,
        speakers = speakers.first(),
        startTimestamp = session.startsAt.toInstant(TimeZone.currentSystemDefault()),
        endTimestamp = session.endsAt.toInstant(TimeZone.currentSystemDefault()),
        isBookmarked = isBookmarked,
      )
    )
  }

  fun bookmark(bookmarked: Boolean) = viewModelScope.launch {
    session.first().onSuccess {
      setSessionBookmarkUseCase(it.id, bookmarked)
    }
  }

  fun shareSession(platformContext: PlatformContext) = viewModelScope.launch {
    session.first().onSuccess {
      shareSessionUseCase(platformContext, it, speakers.first(), formattedDateAndRoom.first())
    }
  }

  fun openLink(platformContext: PlatformContext, socialsItem: SocialsItem) {
    socialsItem.url?.let { openLinkUseCase(platformContext, it) }
  }

  fun applyForAppClinic(platformContext: PlatformContext) {
    applyForAppClinicUseCase(platformContext)
  }
}
