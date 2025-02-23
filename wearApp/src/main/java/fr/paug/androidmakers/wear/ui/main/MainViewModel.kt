package fr.paug.androidmakers.wear.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.wearable.Wearable
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.paug.androidmakers.wear.applicationContext
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import fr.paug.androidmakers.wear.ui.session.UISession
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
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

private val DAY_1_DATE = LocalDate(year = 2024, month = Month.APRIL, dayOfMonth = 25)
private val DAY_2_DATE = DAY_1_DATE.plus(1, DateTimeUnit.DAY)

class MainViewModel(
  application: Application,
  private val userRepository: UserRepository,
  localPreferencesRepository: LocalPreferencesRepository,
  getAgendaUseCase: GetAgendaUseCase,
  private val bookmarksRepository: BookmarksRepository,
  private val sessionsRepository: SessionsRepository,
  getFavoriteSessionsUseCase: GetFavoriteSessionsUseCase,
) : AndroidViewModel(application) {
  private val _user = MutableStateFlow<User?>(null)

  val user: StateFlow<User?> = _user

  private val refreshSignal = Channel<Unit>()

  init {
    viewModelScope.launch {
      _user.emit(userRepository.getUser())
      maybeSyncBookmarks()
      refreshSignal.send(Unit)
    }

    val messageClient = Wearable.getMessageClient(application)
    messageClient.addListener {
      Log.d(TAG, "Received syncBookmarks message")
      viewModelScope.launch {
        maybeSyncBookmarks()
      }
    }
  }

  private suspend fun maybeSyncBookmarks() {
    val currentUser = _user.value
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
  private val sessions: Flow<List<UISession>?> = refreshSignal.consumeAsFlow()
    .flatMapLatest { getAgendaUseCase() }
    .mapNotNull { it.getOrNull() }
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

  val sessionsDay1 =
    sessions.map { sessions -> sessions?.filter { it.session.startsAt.date == DAY_1_DATE } }

  val sessionsDay2 =
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

  fun onSignInSuccess() {
    viewModelScope.launch {
      _user.value = userRepository.getUser()
      maybeSyncBookmarks()
    }
  }

  @OptIn(DelicateCoroutinesApi::class)
  fun signOut() {
    val googleSignInClient = GoogleSignIn.getClient(
      applicationContext,
      GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("985196411897-r7edbi9jgo3hfupekcmdrg66inonj0o5.apps.googleusercontent.com")
        .build()
    )
    googleSignInClient.signOut()
    googleSignInClient.revokeAccess()
    GlobalScope.launch {
      Firebase.auth.signOut()
    }
    _user.value = null
  }

  fun refresh() {
    viewModelScope.launch {
      maybeSyncBookmarks()
      refreshSignal.send(Unit)
    }
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
