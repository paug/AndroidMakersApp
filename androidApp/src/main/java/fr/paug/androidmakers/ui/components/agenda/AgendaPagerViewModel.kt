package fr.paug.androidmakers.ui.components.agenda

import androidx.lifecycle.viewModelScope
import at.asitplus.KmmResult
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.viewmodel.LceViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class AgendaPagerViewModel(
    private val getAgendaUseCase: GetAgendaUseCase,
    private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
    private val getFavoriteSessionsUseCase: GetFavoriteSessionsUseCase
) : LceViewModel<Agenda>() {
  override fun produce(): Flow<KmmResult<Agenda>> {
    return getAgendaUseCase()
  }

  fun getFavoriteSessions() = getFavoriteSessionsUseCase()

  init {
    launch(false)
  }

  fun setSessionBookmark(uiSession: UISession, bookmark: Boolean) = viewModelScope.launch {
    setSessionBookmarkUseCase(uiSession.id, bookmark)
  }
}
