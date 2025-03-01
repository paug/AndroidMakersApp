package com.androidmakers.ui.agenda

import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.common.LceViewModel
import com.androidmakers.ui.common.SessionFilter
import com.androidmakers.ui.model.AgendaPagerState
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.repo.BookmarksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AgendaViewModel(
  getAgendaUseCase: GetAgendaUseCase,
  private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
  bookmarksRepository: BookmarksRepository,
  private val applyForAppClinicUseCase: ApplyForAppClinicUseCase,
) : LceViewModel<AgendaPagerState>(
  produce = { refresh ->
    getAgendaUseCase(refresh)
      .combine(bookmarksRepository.favoriteSessions) { agendaResult, favoriteSessions ->
        agendaResult.map { agenda ->
          AgendaPagerState(
            days = agendaToDays(agenda, favoriteSessions),
            rooms = agenda.rooms
          )
        }
      }
  }
) {
  private val _sessionFilters = MutableStateFlow(emptyList<SessionFilter>())
  val sessionFilters = _sessionFilters.asStateFlow()

  val rooms: Flow<List<Room>> = values.map { stateLce ->
    when (stateLce) {
      is Lce.Content -> stateLce.content.rooms
      else -> emptyList()
    }
  }.distinctUntilChanged()

  fun onFiltersChanged(newSessionFilters: List<SessionFilter>) {
    _sessionFilters.value = newSessionFilters
  }

  fun setSessionBookmark(uiSession: UISession, bookmark: Boolean) = viewModelScope.launch {
    setSessionBookmarkUseCase(uiSession.id, bookmark)
  }

  fun applyForAppClinic(platformContext: PlatformContext) {
    applyForAppClinicUseCase(platformContext)
  }
}
