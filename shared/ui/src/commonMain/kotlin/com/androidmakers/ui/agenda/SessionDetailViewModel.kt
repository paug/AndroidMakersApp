package com.androidmakers.ui.agenda

import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.SessionDetailState
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.OpenLinkUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.utils.formatTimeInterval
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SessionDetailViewModel(
    sessionId: String,
    sessionsRepository: SessionsRepository,
    private val roomsRepository: RoomsRepository,
    // TODO remove the reference to repositories here
    bookmarksRepository: BookmarksRepository,
    private val speakersRepository: SpeakersRepository,
    private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
    private val shareSessionUseCase: ShareSessionUseCase,
    private val openLinkUseCase: OpenLinkUseCase,
    private val applyForAppClinicUseCase: ApplyForAppClinicUseCase,
) : ViewModel() {

  private val session = sessionsRepository.getSession(sessionId)
  private val room = session.mapNotNull { result ->
    result.getOrNull()?.roomId?.let { roomId ->
      roomsRepository.getRoom(roomId)
    }
  }.flattenMerge()

  private val speakers = session.mapNotNull { result ->
    result.getOrNull()?.speakers?.mapNotNull {
      speakersRepository.getSpeaker(it).first().getOrNull()
    }
  }

  private val formattedDateAndRoom = combine(session, room) { sessionResult, roomResult ->
    sessionResult.getOrNull()?.let {
      val sessionDate = formatTimeInterval(
        it.startsAt,
        it.endsAt
      )
      val room = roomResult.getOrNull()?.name
      if (room?.isNotEmpty() == true) {
        "$sessionDate, $room"
      } else {
        sessionDate
      }
    } ?: ""
  }

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
              speakers = speakers.first(),
              startTimestamp = session.getOrThrow().startsAt.toInstant(TimeZone.currentSystemDefault()),
              endTimestamp = session.getOrThrow().endsAt.toInstant(TimeZone.currentSystemDefault()),
              isBookmarked = isBookmarked,
          )
      )
    }
  }

  fun bookmark(bookmarked: Boolean) = viewModelScope.launch {
    setSessionBookmarkUseCase(session.first().getOrThrow().id, bookmarked)
  }

  fun shareSession(platformContext: PlatformContext) = viewModelScope.launch {
    session.first().getOrNull()?.let {
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
