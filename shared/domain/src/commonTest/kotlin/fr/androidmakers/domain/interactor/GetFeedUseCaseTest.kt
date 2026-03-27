package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.FeedItem
import fr.androidmakers.domain.model.MessageType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetFeedUseCaseTest {

    private val feedRepo = FakeFeedRepository()
    private val useCase = GetFeedUseCase(feedRepo)

    @Test
    fun returnsFeedItemsFromRepository() = runTest {
        val items = listOf(
            FeedItem.Message(
                id = "msg-1",
                type = MessageType.INFO,
                title = "Test",
                body = "Body",
                createdAt = Instant.fromEpochMilliseconds(1711800000000L),
            )
        )
        feedRepo.feedItemsFlow.value = Result.success(items)

        val result = useCase().first()

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrThrow().size)
        assertEquals("msg-1", result.getOrThrow()[0].id)
    }

    @Test
    fun returnsEmptyListWhenNoItems() = runTest {
        feedRepo.feedItemsFlow.value = Result.success(emptyList())

        val result = useCase().first()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrThrow().isEmpty())
    }

    @Test
    fun propagatesFailureFromRepository() = runTest {
        feedRepo.feedItemsFlow.value = Result.failure(RuntimeException("Network error"))

        val result = useCase().first()

        assertTrue(result.isFailure)
    }
}
