package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.BookmarksRepository

// TODO temporary, should be merge with the getAgenda and/or getsessions
class GetFavoriteSessionsUseCase(
    private val bookmarksRepository: BookmarksRepository
) {
  operator fun invoke() = bookmarksRepository.favoriteSessions
}
