package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.Venue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetFeedUseCaseTest {

    private val feedRepo = FakeFeedRepository()
    private val venueRepo = FakeVenueRepository()
    private val useCase = GetFeedUseCase(feedRepo, venueRepo)

    private fun testVenue(
        name: String = "Conference Center",
        address: String = "123 Main St",
        description: String = "A great venue",
        floorPlanUrl: String? = null,
    ) = Venue(
        name = name,
        address = address,
        description = description,
        floorPlanUrl = floorPlanUrl,
    )

    private fun testFeedItem(id: String = "feed-1") = FeedItem.Article(
        id = id,
        category = "NEWS",
        timeAgo = "2h ago",
        title = "Test Article",
        description = "Test description",
    )

    @Test
    fun returnsMergedFeedAndVenueItems() = runTest {
        val feedItems = listOf(testFeedItem("feed-1"))
        val conference = testVenue(name = "Beffroi de Montrouge")
        val afterparty = testVenue(name = "Party Place")

        feedRepo.feedItemsFlow.value = Result.success(feedItems)
        venueRepo.conferenceFlow.value = Result.success(conference)
        venueRepo.afterpartyFlow.value = Result.success(afterparty)

        val result = useCase().first()

        assertTrue(result.isSuccess)
        val items = result.getOrThrow()
        // 1 feed item + 1 conference venue + 1 afterparty venue = 3
        assertEquals(3, items.size)
        assertEquals("feed-1", items[0].id)
        assertEquals("venue-conference", items[1].id)
        assertEquals("venue-afterparty", items[2].id)
    }

    @Test
    fun returnsOnlyVenueItemsWhenFeedIsEmpty() = runTest {
        val conference = testVenue()
        val afterparty = testVenue(name = "Party")

        feedRepo.feedItemsFlow.value = Result.success(emptyList())
        venueRepo.conferenceFlow.value = Result.success(conference)
        venueRepo.afterpartyFlow.value = Result.success(afterparty)

        val result = useCase().first()

        assertTrue(result.isSuccess)
        val items = result.getOrThrow()
        assertEquals(2, items.size)
        assertEquals("venue-conference", items[0].id)
        assertEquals("venue-afterparty", items[1].id)
    }

    @Test
    fun returnsSuccessWithEmptyListWhenFeedFailsAndVenuesFail() = runTest {
        feedRepo.feedItemsFlow.value = Result.failure(RuntimeException("Network error"))
        venueRepo.conferenceFlow.value = Result.failure(RuntimeException("Venue error"))
        venueRepo.afterpartyFlow.value = Result.failure(RuntimeException("Venue error"))

        val result = useCase().first()

        assertTrue(result.isSuccess)
        val items = result.getOrThrow()
        assertTrue(items.isEmpty())
    }

    @Test
    fun includesFloorPlanWhenVenueHasFloorPlanUrl() = runTest {
        val conference = testVenue(floorPlanUrl = "https://example.com/floorplan.png")

        feedRepo.feedItemsFlow.value = Result.success(emptyList())
        venueRepo.conferenceFlow.value = Result.success(conference)
        venueRepo.afterpartyFlow.value = Result.failure(NoSuchElementException())

        val result = useCase().first()

        assertTrue(result.isSuccess)
        val items = result.getOrThrow()
        assertEquals(2, items.size)
        assertEquals("venue-conference", items[0].id)
        assertEquals("venue-floorplan", items[1].id)
        val floorPlan = items[1] as FeedItem.Article
        assertEquals("https://example.com/floorplan.png", floorPlan.thumbnailUrl)
    }

    @Test
    fun doesNotIncludeFloorPlanWhenVenueHasNoFloorPlanUrl() = runTest {
        val conference = testVenue(floorPlanUrl = null)

        feedRepo.feedItemsFlow.value = Result.success(emptyList())
        venueRepo.conferenceFlow.value = Result.success(conference)
        venueRepo.afterpartyFlow.value = Result.failure(NoSuchElementException())

        val result = useCase().first()

        assertTrue(result.isSuccess)
        val items = result.getOrThrow()
        assertEquals(1, items.size)
        assertEquals("venue-conference", items[0].id)
    }

    @Test
    fun venueConferenceItemHasLocationWithAddress() = runTest {
        val conference = testVenue(address = "10 rue de la Paix")

        feedRepo.feedItemsFlow.value = Result.success(emptyList())
        venueRepo.conferenceFlow.value = Result.success(conference)
        venueRepo.afterpartyFlow.value = Result.failure(NoSuchElementException())

        val result = useCase().first()

        assertTrue(result.isSuccess)
        val item = result.getOrThrow().first() as FeedItem.Article
        assertEquals("10 rue de la Paix", item.location?.name)
        assertEquals("", item.timeAgo)
    }
}
