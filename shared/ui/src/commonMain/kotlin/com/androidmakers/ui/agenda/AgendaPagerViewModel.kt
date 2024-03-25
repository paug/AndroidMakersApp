package com.androidmakers.ui.agenda

import com.androidmakers.ui.common.LceViewModel
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Agenda
import com.androidmakers.ui.model.UISession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class AgendaPagerViewModel(
    private val getAgendaUseCase: GetAgendaUseCase,
    private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
    private val getFavoriteSessionsUseCase: GetFavoriteSessionsUseCase
) : LceViewModel<Agenda>() {
  override fun produce(): Flow<Result<Agenda>> {
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
