package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetAgendaUseCaseTest {

    private val sessionsRepo = FakeSessionsRepository()
    private val speakersRepo = FakeSpeakersRepository()
    private val roomsRepo = FakeRoomsRepository()
    private val useCase = GetAgendaUseCase(sessionsRepo, speakersRepo, roomsRepo)

    private fun testSession(id: String = "1", title: String = "Test") = Session(
        id = id,
        title = title,
        startsAt = LocalDateTime(2025, 4, 25, 10, 0),
        endsAt = LocalDateTime(2025, 4, 25, 10, 40),
        roomId = "room1",
        isServiceSession = false,
        type = "talk",
    )

    @Test
    fun returnsAgendaWhenAllSourcesSucceed() = runTest {
        val sessions = listOf(testSession("1", "Session 1"))
        val rooms = listOf(Room("room1", "Room 1"))
        val speakers = listOf(Speaker(id = "s1", name = "Speaker 1"))

        sessionsRepo.sessionsFlow.value = Result.success(sessions)
        roomsRepo.roomsFlow.value = Result.success(rooms)
        speakersRepo.speakersFlow.value = Result.success(speakers)

        val result = useCase(refresh = false).first()

        assertTrue(result.isSuccess)
        val agenda = result.getOrThrow()
        assertEquals(1, agenda.sessions.size)
        assertEquals("Session 1", agenda.sessions[0].title)
        assertEquals(1, agenda.rooms.size)
        assertEquals("Room 1", agenda.rooms[0].name)
        assertEquals(1, agenda.speakers.size)
        assertEquals("Speaker 1", agenda.speakers[0].name)
    }

    @Test
    fun returnsFailureWhenSessionsFail() = runTest {
        val error = RuntimeException("Network error")
        sessionsRepo.sessionsFlow.value = Result.failure(error)
        roomsRepo.roomsFlow.value = Result.success(listOf(Room("room1", "Room 1")))
        speakersRepo.speakersFlow.value = Result.success(listOf(Speaker(id = "s1", name = "Speaker")))

        val result = useCase(refresh = false).first()

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun returnsFailureWhenRoomsFail() = runTest {
        val error = RuntimeException("Rooms error")
        sessionsRepo.sessionsFlow.value = Result.success(listOf(testSession()))
        roomsRepo.roomsFlow.value = Result.failure(error)
        speakersRepo.speakersFlow.value = Result.success(emptyList())

        val result = useCase(refresh = false).first()

        assertTrue(result.isFailure)
        assertEquals("Rooms error", result.exceptionOrNull()?.message)
    }

    @Test
    fun returnsFailureWhenSpeakersFail() = runTest {
        val error = RuntimeException("Speakers error")
        sessionsRepo.sessionsFlow.value = Result.success(listOf(testSession()))
        roomsRepo.roomsFlow.value = Result.success(listOf(Room("room1", "Room 1")))
        speakersRepo.speakersFlow.value = Result.failure(error)

        val result = useCase(refresh = false).first()

        assertTrue(result.isFailure)
        assertEquals("Speakers error", result.exceptionOrNull()?.message)
    }

    @Test
    fun returnsEmptyAgendaWhenAllSourcesReturnEmpty() = runTest {
        sessionsRepo.sessionsFlow.value = Result.success(emptyList())
        roomsRepo.roomsFlow.value = Result.success(emptyList())
        speakersRepo.speakersFlow.value = Result.success(emptyList())

        val result = useCase(refresh = false).first()

        assertTrue(result.isSuccess)
        val agenda = result.getOrThrow()
        assertTrue(agenda.sessions.isEmpty())
        assertTrue(agenda.rooms.isEmpty())
        assertTrue(agenda.speakers.isEmpty())
    }
}
