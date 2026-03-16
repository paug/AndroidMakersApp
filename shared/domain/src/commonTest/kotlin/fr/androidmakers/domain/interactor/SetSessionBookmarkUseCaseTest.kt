package fr.androidmakers.domain.interactor

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SetSessionBookmarkUseCaseTest {

    private val userRepo = FakeUserRepository()
    private val sessionsRepo = FakeSessionsRepository()
    private val bookmarksRepo = FakeBookmarksRepository()
    private val messagingRepo = FakeMessagingRepository()
    private val useCase = SetSessionBookmarkUseCase(userRepo, sessionsRepo, bookmarksRepo, messagingRepo)

    @Test
    fun bookmarkUpdatesLocalAndRemote() = runTest {
        useCase("session1", true)

        assertTrue(bookmarksRepo.isBookmarked("session1").first())
        assertEquals(Triple("test-user", "session1", true), sessionsRepo.lastBookmarkCall)
        assertTrue(messagingRepo.syncMessageSent)
    }

    @Test
    fun removeBookmarkUpdatesLocalAndRemote() = runTest {
        bookmarksRepo.setBookmarked("session1", true)

        useCase("session1", false)

        assertTrue(!bookmarksRepo.isBookmarked("session1").first())
        assertEquals(Triple("test-user", "session1", false), sessionsRepo.lastBookmarkCall)
    }

    @Test
    fun noRemoteCallWhenUserIsNull() = runTest {
        userRepo.currentUser = null

        useCase("session1", true)

        assertTrue(bookmarksRepo.isBookmarked("session1").first())
        assertEquals(null, sessionsRepo.lastBookmarkCall)
    }
}
