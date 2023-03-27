package fr.paug.androidmakers.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.model.Room
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.util.SessionFilter
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

class AgendaLayoutViewModel(
    store: AndroidMakersStore = AndroidMakersApplication.instance().store,
    scope: ViewModel.() -> CoroutineScope = { viewModelScope }
) : ViewModel() {
  private val sessionFilters = MutableStateFlow(emptyList<SessionFilter>())

  val state: StateFlow<AgendaLayoutState> = combine(
      store.getRooms().map { rooms -> rooms.recover { emptyList() }.getOrThrow() },
      sessionFilters,
      ::AgendaLayoutState
  ).stateIn(scope(), SharingStarted.WhileSubscribed(), AgendaLayoutState())

  fun onFiltersChanged(sessionFilters: List<SessionFilter>) {
    this.sessionFilters.value = sessionFilters
  }
}
