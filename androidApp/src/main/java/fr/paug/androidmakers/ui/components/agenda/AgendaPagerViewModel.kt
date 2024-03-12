package fr.paug.androidmakers.ui.components.agenda

import at.asitplus.KmmResult
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import fr.androidmakers.domain.model.Agenda
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.paug.androidmakers.ui.viewmodel.LceViewModel
import kotlinx.coroutines.flow.Flow

class AgendaPagerViewModel(
    private val getAgendaUseCase: GetAgendaUseCase,
    private val bookmarksRepository: BookmarksRepository
) : LceViewModel<Agenda>() {
  override fun produce(): Flow<KmmResult<Agenda>> {
    return getAgendaUseCase()
  }

  fun getFavoriteSessions() = bookmarksRepository.getFavoriteSessions()

  init {
    launch(false)
  }
}
