package fr.paug.androidmakers.wear.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import java.time.Month

private val TAG = MainViewModel::class.java.simpleName

// TODO Update this date with 2024 edition date!
private val DAY_1_DATE = LocalDate(year = 2023, month = Month.APRIL, dayOfMonth = 27)
private val DAY_2_DATE = DAY_1_DATE.plus(1, DateTimeUnit.DAY)

class MainViewModel(
    application: Application,
    private val userRepository: UserRepository,
    private val getAgendaUseCase: GetAgendaUseCase,
    val syncBookmarksUseCase: SyncBookmarksUseCase,
) : AndroidViewModel(application) {
  private val _user = MutableStateFlow<User?>(null)

  val user: StateFlow<User?> = _user

  init {
    viewModelScope.launch {
      _user.emit(userRepository.getUser())

      val currentUser = _user.value
      if (currentUser != null) {
        // fire & forget
        // This is racy but oh well...
        syncBookmarksUseCase(currentUser.id)
      }
    }
  }

  private val sessions: Flow<List<UISession>> = getAgendaUseCase()
      .filter { it.isSuccess }
      .map { it.getOrThrow().toUISessions() }

  val sessionsDay1 = sessions.map { sessions -> sessions.filter { it.session.startsAt.date == DAY_1_DATE } }

  val sessionsDay2 = sessions.map { sessions -> sessions.filter { it.session.startsAt.date == DAY_2_DATE } }

  /**
   * Get the day of the conference, based on the current date.
   * If the date is the first day of the conference or earlier, returns 0, otherwise returns 1.
   */
  fun getConferenceDay(): Int {
    return if (Clock.System.todayIn(TimeZone.currentSystemDefault()) <= DAY_1_DATE) {
      0
    } else {
      1
    }
  }
}

private fun Agenda.toUISessions(): List<UISession> {
  return sessions.values.mapNotNull { session ->
    UISession(
        session = session,
        speakers = session.speakers.map {
          speakers[it] ?: run {
            Log.d(TAG, "Speaker $it not found")
            return@mapNotNull null
          }
        },
        room = rooms[session.roomId] ?: run {
          Log.d(TAG, "Room ${session.roomId} not found")
          return@mapNotNull null
        }
    )
  }
}
