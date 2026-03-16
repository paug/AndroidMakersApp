package fr.androidmakers.domain.interactor

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetFavoriteSessionsUseCaseTest {

    private val bookmarksRepo = FakeBookmarksRepository()
    private val useCase = GetFavoriteSessionsUseCase(bookmarksRepo)

    @Test
    fun returnsFavoriteSessions() = runTest {
        bookmarksRepo.setBookmarked("s1", true)
        bookmarksRepo.setBookmarked("s2", true)

        val favorites = useCase().first()

        assertEquals(setOf("s1", "s2"), favorites)
    }

    @Test
    fun returnsEmptyWhenNoBookmarks() = runTest {
        val favorites = useCase().first()
        assertTrue(favorites.isEmpty())
    }
}
