package fr.androidmakers.domain.interactor

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MergeBookmarksUseCaseTest {

    private val bookmarksRepo = FakeBookmarksRepository()
    private val sessionsRepo = FakeSessionsRepository()
    private val useCase = MergeBookmarksUseCase(bookmarksRepo, sessionsRepo)

    @Test
    fun mergesRemoteBookmarksIntoLocal() = runTest {
        bookmarksRepo.setBookmarked("local1", true)
        sessionsRepo.bookmarksFlow.value = Result.success(setOf("remote1", "remote2"))

        useCase("user1")

        val favorites = bookmarksRepo.favoriteSessions.first()
        assertTrue("local1" in favorites)
        assertTrue("remote1" in favorites)
        assertTrue("remote2" in favorites)
    }

    @Test
    fun doesNothingWhenRemoteBookmarksFail() = runTest {
        bookmarksRepo.setBookmarked("local1", true)
        sessionsRepo.bookmarksFlow.value = Result.failure(RuntimeException("Network error"))

        useCase("user1")

        val favorites = bookmarksRepo.favoriteSessions.first()
        assertEquals(setOf("local1"), favorites)
    }
}
