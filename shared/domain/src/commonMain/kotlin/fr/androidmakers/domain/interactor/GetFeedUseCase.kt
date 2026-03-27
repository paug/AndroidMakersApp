package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.repo.FeedRepository
import kotlinx.coroutines.flow.Flow

class GetFeedUseCase(
  private val feedRepository: FeedRepository,
) {
  operator fun invoke(): Flow<Result<List<FeedItem>>> = feedRepository.getFeedItems()
}
