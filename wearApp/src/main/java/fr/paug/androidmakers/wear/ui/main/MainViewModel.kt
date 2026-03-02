package fr.paug.androidmakers.wear.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageClient.OnMessageReceivedListener
import com.google.android.gms.wearable.MessageEvent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.domain.utils.formatMediumDate
import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import fr.paug.androidmakers.wear.ui.session.UISession
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn

private val TAG = MainViewModel::class.java.simpleName

class MainViewModel(
  messageClient: MessageClient,
  userRepository: UserRepository,
  private val googleSignInClient: GoogleSignInClient,
  localPreferencesRepository: LocalPreferencesRepository,
  getAgendaUseCase: GetAgendaUseCase,
  private val bookmarksRepository: BookmarksRepository,
  private val sessionsRepository: SessionsRepository,
) : ViewModel() {
  val user: StateFlow<User?> = userRepository.user.stateIn(
    scope = viewModelScope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue = userRepository.currentUser
  )

  private class LoadingTrigger(val refresh: Boolean)

  private val bookmarksSyncTrigger = MutableStateFlow(LoadingTrigger(refresh = true))
  private val sessionsLoadingTrigger = MutableStateFlow(LoadingTrigger(refresh = false))

  init {
    // Sync bookmarks when user changes or sync is triggered
    @OptIn(ExperimentalCoroutinesApi::class)
    combine(user, bookmarksSyncTrigger) { currentUser, _ -> currentUser }
      .flatMapLatest { currentUser ->
        flow { emit(maybeSyncBookmarks(currentUser)) }
      }
      .launchIn(viewModelScope)

    // Listen for sync messages from the phone app
    messageClient.getEventsFlow()
      .onEach { messageEvent ->
        if (messageEvent.path == MESSAGE_SYNC_BOOKMARKS) {
          Log.d(TAG, "Received syncBookmarks message")
          bookmarksSyncTrigger.value = LoadingTrigger(refresh = true)
        }
      }
      .launchIn(viewModelScope)
  }

  private fun MessageClient.getEventsFlow(): Flow<MessageEvent> = callbackFlow<MessageEvent> {
    val listener = OnMessageReceivedListener { messageEvent ->
      trySend(messageEvent)
    }
    addListener(listener)
    awaitClose { removeListener(listener) }
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
  private val sessions: StateFlow<List<UISession>?> = combine(
    sessionsLoadingTrigger
      .flatMapLatest { trigger -> getAgendaUseCase(trigger.refresh) }
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
      started = SharingStarted.WhileSubscribed(5_000),
      initialValue = null
    )

  val days: StateFlow<List<WearDaySchedule>?> =
    sessions.map { sessions ->
      sessions
        ?.groupBy { it.session.startsAt.date }
        ?.toSortedMap()
        ?.map { (date, daySessions) ->
          WearDaySchedule(
            title = date.formatMediumDate(),
            date = date,
            sessions = daySessions
          )
        }
    }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
      )

  /**
   * Get the day of the conference to display initially, based on the current date.
   * Returns the index of the first day that is today or in the future,
   * or the last day index if all days are in the past.
   */
  fun getConferenceDay(days: List<WearDaySchedule>): Int {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    val index = days.indexOfFirst { it.date >= today }
    return if (index >= 0) index else days.lastIndex.coerceAtLeast(0)
  }

  fun signOut() {
    viewModelScope.launch(NonCancellable) {
      googleSignInClient.signOut()
      googleSignInClient.revokeAccess()
      Firebase.auth.signOut()
    }
  }

  fun refresh() {
    bookmarksSyncTrigger.value = LoadingTrigger(refresh = true)
    sessionsLoadingTrigger.value = LoadingTrigger(refresh = true)
  }

  companion object {
    private const val MESSAGE_SYNC_BOOKMARKS = "syncBookmarks"
  }
}

data class WearDaySchedule(
  val title: String,
  val date: LocalDate,
  val sessions: List<UISession>,
)

private fun Agenda.toUISessions(favoriteSessions: Set<String>): List<UISession> {
  val speakersById = speakers.associateBy { it.id }
  val roomsById = rooms.associateBy { it.id }
  return sessions.mapNotNull { session ->
    UISession(
      session = session,
      speakers = session.speakers.map {
        speakersById[it] ?: run {
          Log.d(TAG, "Speaker $it not found")
          return@mapNotNull null
        }
      },
      room = roomsById[session.roomId] ?: run {
        Log.d(TAG, "Room ${session.roomId} not found")
        return@mapNotNull null
      },
      isBookmarked = session.id in favoriteSessions,
    )
  }
}
