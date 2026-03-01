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
    category = "VENUE",
    categoryBadge = "VENUE",
    title = name,
    description = description,
    imageUrl = imageUrl,
    timeAgo = address,
  )

  private fun Venue.toAfterpartyFeedItem() = FeedItem.Article(
    id = "venue-afterparty",
    category = "EVENT",
    title = name,
    description = description,
    location = LocationInfo(address, ""),
    timeAgo = address,
  )

  private fun Venue.toFloorPlanFeedItem() = FeedItem.Article(
    id = "venue-floorplan",
    category = "VENUE",
    title = "Floor Plan",
    description = name,
    thumbnailUrl = floorPlanUrl,
    timeAgo = name,
  )
}
