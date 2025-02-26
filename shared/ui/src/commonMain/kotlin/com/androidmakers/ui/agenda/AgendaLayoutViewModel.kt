package com.androidmakers.ui.agenda

import com.androidmakers.ui.common.SessionFilter
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.repo.RoomsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

data class AgendaLayoutState(
  val rooms: List<Room> = emptyList(),
  val sessionFilters: List<SessionFilter> = emptyList()
)

class AgendaLayoutViewModel(
    roomsRepository: RoomsRepository,
    scope: ViewModel.() -> CoroutineScope = { viewModelScope }
) : ViewModel() {
  private val sessionFilters = MutableStateFlow(emptyList<SessionFilter>())

  val state: StateFlow<AgendaLayoutState> = combine(
    roomsRepository.getRooms()
      .map { it.getOrNull().orEmpty() },
    sessionFilters,
    ::AgendaLayoutState
  ).stateIn(
    scope = scope(),
    started = SharingStarted.Eagerly,
    initialValue = AgendaLayoutState()
  )

  fun onFiltersChanged(newSessionFilters: List<SessionFilter>) {
    sessionFilters.value = newSessionFilters
  }
}
