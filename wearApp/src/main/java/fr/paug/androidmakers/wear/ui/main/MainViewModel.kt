package fr.paug.androidmakers.wear.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.wearable.Wearable
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import fr.paug.androidmakers.wear.ui.session.UISession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import java.time.Month

private val TAG = MainViewModel::class.java.simpleName

private val DAY_1_DATE = LocalDate(year = 2024, month = Month.APRIL, dayOfMonth = 25)
private val DAY_2_DATE = DAY_1_DATE.plus(1, DateTimeUnit.DAY)

class MainViewModel(
  application: Application,
  userRepository: UserRepository,
  private val googleSignInClient: GoogleSignInClient,
  localPreferencesRepository: LocalPreferencesRepository,
  getAgendaUseCase: GetAgendaUseCase,
  private val bookmarksRepository: BookmarksRepository,
  private val sessionsRepository: SessionsRepository,
) : ViewModel() {
  val user: StateFlow<User?> = userRepository.user.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = userRepository.currentUser
  )

  private val sessionsRefreshTrigger = MutableStateFlow(0)
  private val bookmarksRefreshTrigger = MutableStateFlow(0)

  init {
    viewModelScope.launch {
      // Sync bookmarks when the user changes or a refresh is requested
      combine(user, bookmarksRefreshTrigger) { currentUser, trigger -> currentUser }
        .collectLatest { currentUser -> maybeSyncBookmarks(currentUser) }
    }

    val messageClient = Wearable.getMessageClient(application)
    messageClient.addListener {
      Log.d(TAG, "Received syncBookmarks message")
      bookmarksRefreshTrigger.update { it + 1 }
    }
  }

  private suspend fun maybeSyncBookmarks(currentUser: User?) {
    if (currentUser != null) {
      Log.d(TAG, "Syncing bookmarks")
      val bookmarks = sessionsRepository.getBookmarks(currentUser.id).first()
      bookmarks.onSuccess {
        bookmarksRepository.setBookmarks(it)
      }
      Log.d(TAG, "Bookmarks synced")
    }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  private val sessions: Flow<List<UISession>?> = combine(
    sessionsRefreshTrigger
      .flatMapLatest { getAgendaUseCase() }
      .mapNotNull { it.getOrNull() },
    bookmarksRepository.favoriteSessions,
    localPreferencesRepository.showOnlyBookmarkedSessions
  ) { agenda, favoriteSessions, showOnlyBookmarked ->
    val sessions = agenda.toUISessions(favoriteSessions)
    if (showOnlyBookmarked) {
      sessions.filter { it.isBookmarked }
    } else {
      sessions
    }
  }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.Eagerly,
      initialValue = null
    )

  val sessionsDay1: Flow<List<UISession>?> =
    sessions.map { sessions -> sessions?.filter { it.session.startsAt.date == DAY_1_DATE } }

  val sessionsDay2: Flow<List<UISession>?> =
    sessions.map { sessions -> sessions?.filter { it.session.startsAt.date == DAY_2_DATE } }

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

  fun signOut() {
    viewModelScope.launch(NonCancellable) {
      googleSignInClient.signOut()
      googleSignInClient.revokeAccess()
      Firebase.auth.signOut()
    }
  }

  fun refresh() {
    sessionsRefreshTrigger.update { it + 1 }
    bookmarksRefreshTrigger.update { it + 1 }
  }
}

private fun Agenda.toUISessions(favoriteSessions: Set<String>): List<UISession> {
  return sessions.mapNotNull { session ->
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
