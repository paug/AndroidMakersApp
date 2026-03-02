package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.LocationInfo
import fr.androidmakers.domain.model.Venue
import fr.androidmakers.domain.repo.FeedRepository
import fr.androidmakers.domain.repo.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetFeedUseCase(
  private val feedRepository: FeedRepository,
  private val venueRepository: VenueRepository,
) {
  companion object {
    const val CATEGORY_VENUE = "VENUE"
    const val CATEGORY_EVENT = "EVENT"
    const val TITLE_FLOOR_PLAN = "Floor Plan"
  }

  operator fun invoke(): Flow<Result<List<FeedItem>>> = combine(
    feedRepository.getFeedItems(),
    venueRepository.getVenue("conference"),
    venueRepository.getVenue("afterparty"),
  ) { feedResult, conferenceResult, afterpartyResult ->
    val feedItems = feedResult.getOrNull().orEmpty()
    val venueItems = buildList {
      conferenceResult.getOrNull()?.let { venue ->
        add(venue.toConferenceFeedItem())
        venue.floorPlanUrl?.let { add(venue.toFloorPlanFeedItem()) }
      }
      afterpartyResult.getOrNull()?.let { venue ->
        add(venue.toAfterpartyFeedItem())
      }
    }
    Result.success(feedItems + venueItems)
  }

  private fun Venue.toConferenceFeedItem() = FeedItem.Article(
    id = "venue-conference",
    category = CATEGORY_VENUE,
    categoryBadge = CATEGORY_VENUE,
    title = name,
    description = description,
    imageUrl = imageUrl,
    timeAgo = "",
    location = LocationInfo(name = address),
  )

  private fun Venue.toAfterpartyFeedItem() = FeedItem.Article(
    id = "venue-afterparty",
    category = CATEGORY_EVENT,
    title = name,
    description = description,
    timeAgo = "",
    location = LocationInfo(name = address),
  )

  private fun Venue.toFloorPlanFeedItem() = FeedItem.Article(
    id = "venue-floorplan",
    category = CATEGORY_VENUE,
    title = TITLE_FLOOR_PLAN,
    description = name,
    thumbnailUrl = floorPlanUrl,
    timeAgo = "",
  )
}
