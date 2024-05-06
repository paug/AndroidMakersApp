package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.SessionsRepository

class FetchNextSessionsPageUseCase(
  private val sessionsRepository: SessionsRepository,
) {
  suspend operator fun invoke() {
    sessionsRepository.fetchNextSessionsPage()
  }
}
