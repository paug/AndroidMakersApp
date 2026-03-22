package com.androidmakers.ui.agenda

import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.common.LceViewModel
import com.androidmakers.ui.common.SessionFilter
import com.androidmakers.ui.model.AgendaState
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.MergeBookmarksUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.domain.utils.UrlOpener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AgendaViewModel(
  getAgendaUseCase: GetAgendaUseCase,
  mergeBookmarksUseCase: MergeBookmarksUseCase,
  private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
  bookmarksRepository: BookmarksRepository,
  userRepository: UserRepository,
  private val applyForAppClinicUseCase: ApplyForAppClinicUseCase,
) : LceViewModel<AgendaState>(
  produce = { refresh ->
    getAgendaUseCase(refresh)
      .combine(
        flow {
          userRepository.currentUser?.let {
            mergeBookmarksUseCase(it.id)
          }
          emitAll(bookmarksRepository.favoriteSessions)
        }
      ) { agendaResult, favoriteSessions ->
        agendaResult.map { agenda ->
          AgendaState(
            days = agendaToDays(agenda, favoriteSessions),
            rooms = agenda.rooms
          )
        }
      }
  }
) {
  private val _sessionFilters = MutableStateFlow(emptySet<SessionFilter>())
  val sessionFilters: StateFlow<Set<SessionFilter>> = _sessionFilters.asStateFlow()

  val rooms: Flow<List<Room>> = values.map { stateLce ->
    when (stateLce) {
      is Lce.Content -> stateLce.content.rooms
      else -> emptyList()
    }
  }.distinctUntilChanged()

  val tags: Flow<List<String>> = values.map { stateLce ->
    when (stateLce) {
      is Lce.Content -> stateLce.content.days
        .flatMap { day -> day.sessions }
        .filter { !it.isServiceSession }
        .flatMap { it.tags }
        .distinct()
        .sorted()
      else -> emptyList()
    }
  }.distinctUntilChanged()

  fun onFiltersChanged(newSessionFilters: Set<SessionFilter>) {
    _sessionFilters.value = newSessionFilters
  }

  fun setSessionBookmark(uiSession: UISession, bookmark: Boolean) = viewModelScope.launch {
    setSessionBookmarkUseCase(uiSession.id, bookmark)
  }

  fun applyForAppClinic(urlOpener: UrlOpener) {
    applyForAppClinicUseCase(urlOpener)
  }
}
