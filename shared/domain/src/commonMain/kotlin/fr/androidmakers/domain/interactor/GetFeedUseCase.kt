package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.FeedRepository

class GetFeedUseCase(
  private val feedRepository: FeedRepository
) {
  operator fun invoke() = feedRepository.getFeedItems()
}
