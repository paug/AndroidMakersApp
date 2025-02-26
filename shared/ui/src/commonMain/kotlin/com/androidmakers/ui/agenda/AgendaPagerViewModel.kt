package com.androidmakers.ui.agenda

import com.androidmakers.ui.common.LceViewModel
import com.androidmakers.ui.model.UISession
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.model.Agenda
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.viewModelScope

class AgendaPagerViewModel(
  getAgendaUseCase: GetAgendaUseCase,
  private val setSessionBookmarkUseCase: SetSessionBookmarkUseCase,
  private val getFavoriteSessionsUseCase: GetFavoriteSessionsUseCase,
  private val applyForAppClinicUseCase: ApplyForAppClinicUseCase,
) : LceViewModel<Agenda>(
  produce = { getAgendaUseCase() }
) {
  fun getFavoriteSessions() = getFavoriteSessionsUseCase()

  fun setSessionBookmark(uiSession: UISession, bookmark: Boolean) = viewModelScope.launch {
    setSessionBookmarkUseCase(uiSession.id, bookmark)
  }

  fun applyForAppClinic(platformContext: PlatformContext) {
    applyForAppClinicUseCase(platformContext)
  }
}
