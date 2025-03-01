package com.androidmakers.ui.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

data class AgendaLayoutState(
  val rooms: List<Room> = emptyList(),
  val sessionFilters: List<SessionFilter> = emptyList()
)

class AgendaLayoutViewModel : ViewModel {
  private val sessionFilters = MutableStateFlow(emptyList<SessionFilter>())

  val state: StateFlow<AgendaLayoutState>

  constructor(
    roomsRepository: RoomsRepository
  ) : super() {
    state = createState(roomsRepository)
  }

  constructor(
    roomsRepository: RoomsRepository,
    coroutineScope: CoroutineScope
  ) : super(coroutineScope) {
    state = createState(roomsRepository)
  }

  private fun createState(
    roomsRepository: RoomsRepository
  ): StateFlow<AgendaLayoutState> = combine(
    roomsRepository.getRooms(false)
      .map { it.getOrNull().orEmpty() },
    sessionFilters,
    ::AgendaLayoutState
  ).stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = AgendaLayoutState()
  )

  fun onFiltersChanged(newSessionFilters: List<SessionFilter>) {
    sessionFilters.value = newSessionFilters
  }
}
