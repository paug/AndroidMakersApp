package fr.paug.androidmakers.wear.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import fr.paug.androidmakers.wear.R
import fr.paug.androidmakers.wear.applicationContext
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    localPreferencesRepository: LocalPreferencesRepository,
    getAgendaUseCase: GetAgendaUseCase,
    private val syncBookmarksUseCase: SyncBookmarksUseCase,
    getFavoriteSessionsUseCase: GetFavoriteSessionsUseCase,
) : AndroidViewModel(application) {
  private val _user = MutableStateFlow<User?>(null)

  val user: StateFlow<User?> = _user

  init {
    viewModelScope.launch {
      _user.emit(userRepository.getUser())
      maybeSyncBookmarks()
    }
  }

  private suspend fun maybeSyncBookmarks() {
    val currentUser = _user.value
    if (currentUser != null) {
      Log.d(TAG, "Syncing bookmarks")
      syncBookmarksUseCase(currentUser.id)
      Log.d(TAG, "Bookmarks synced")
    }
  }

  private val sessions: Flow<List<UISession>?> = getAgendaUseCase()
      .filter { it.isSuccess }
      .map { it.getOrThrow() }
      .combine(getFavoriteSessionsUseCase()) { agenda, favoriteSessions ->
        agenda.toUISessions(favoriteSessions)
      }
      .combine(localPreferencesRepository.showOnlyBookmarkedSessions) { sessions, showOnlyBookmarked ->
        if (showOnlyBookmarked) {
          sessions.filter { it.isBookmarked }
        } else {
          sessions
        }
      }
      .stateIn(viewModelScope, SharingStarted.Lazily, null)

  val sessionsDay1 = sessions.map { sessions -> sessions?.filter { it.session.startsAt.date == DAY_1_DATE } }

  val sessionsDay2 = sessions.map { sessions -> sessions?.filter { it.session.startsAt.date == DAY_2_DATE } }

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

  fun onSignInSuccess() {
    _user.value = userRepository.getUser()
    viewModelScope.launch {
      maybeSyncBookmarks()
    }
  }

  fun signOut() {
    val googleSignInClient = GoogleSignIn.getClient(
        applicationContext,
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(applicationContext.getString(R.string.default_web_client_id))
            .build()
    )
    googleSignInClient.signOut()
    googleSignInClient.revokeAccess()
    Firebase.auth.signOut()
    _user.value = null
  }
}

private fun Agenda.toUISessions(favoriteSessions: Set<String>): List<UISession> {
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
        },
        isBookmarked = session.id in favoriteSessions,
    )
  }
}
