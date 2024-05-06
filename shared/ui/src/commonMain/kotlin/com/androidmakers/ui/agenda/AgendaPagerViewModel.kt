package com.androidmakers.ui.agenda

import com.androidmakers.ui.common.LceViewModel
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.FetchNextSessionsPageUseCase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Agenda
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class AgendaPagerViewModel(
  private val getAgendaUseCase: GetAgendaUseCase,
  private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
  private val fetchNextSessionsPageUseCase: FetchNextSessionsPageUseCase,
  private val getFavoriteSessionsUseCase: GetFavoriteSessionsUseCase,
  private val applyForAppClinicUseCase: ApplyForAppClinicUseCase,
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

  fun applyForAppClinic(platformContext: PlatformContext) {
    applyForAppClinicUseCase(platformContext)
  }

  fun fetchNextSessionsPage() = viewModelScope.launch {
    fetchNextSessionsPageUseCase()
  }
}
