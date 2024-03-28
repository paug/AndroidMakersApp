package fr.paug.androidmakers.ui.components

import com.androidmakers.ui.agenda.AgendaLayoutState
import com.androidmakers.ui.agenda.AgendaLayoutViewModel
import com.androidmakers.ui.common.SessionFilter
import fr.androidmakers.domain.model.Room
import fr.paug.androidmakers.fixtures.FakeAndroidMakersStore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AgendaLayoutViewModelTest {

  private val fakeStore = FakeAndroidMakersStore()

  private val testSubject = AgendaLayoutViewModel(
    roomsRepository = fakeStore,
    scope = { CoroutineScope(Dispatchers.Unconfined) }
  )

  @Test
  fun `initial state should be correct`() = runBlocking {
    assertEquals(
      AgendaLayoutState(
        rooms = emptyList(),
        sessionFilters = emptyList()
      ),
      testSubject.state.first()
    )
  }

  @Test
  fun `given non empty session filters state should be correct`() = runBlocking {
    testSubject.onFiltersChanged(
      listOf(
        SessionFilter(
          SessionFilter.FilterType.BOOKMARK,
          "bookmark"
        )
      )
    )

    assertEquals(
      AgendaLayoutState(
        rooms = emptyList(),
        sessionFilters = listOf(
          SessionFilter(
            SessionFilter.FilterType.BOOKMARK,
            "bookmark"
          )
        )
      ),
      testSubject.state.first()
    )
  }

  @Test
  fun `given failure in retrieving rooms state should have empty rooms`() = runBlocking {
    fakeStore.roomsMutableFlow.value = Result.failure(RuntimeException("exception"))

    assertEquals(
      AgendaLayoutState(
        rooms = emptyList(),
        sessionFilters = emptyList()
      ),
      testSubject.state.first()
    )
  }

  @Test
  fun `given rooms are retrieved state should be correct`() = runBlocking {
    fakeStore.roomsMutableFlow.value = Result.success(listOf(Room(id = "id", name = "name")))

    assertEquals(
      AgendaLayoutState(
        rooms = listOf(Room(id = "id", name = "name")),
        sessionFilters = emptyList()
      ),
      testSubject.state.first()
    )
  }
}
