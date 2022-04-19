package fr.androidmakers.store

import fr.androidmakers.store.model.Agenda
import fr.androidmakers.store.model.Partner
import fr.androidmakers.store.model.Room
import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Session
import fr.androidmakers.store.model.Speaker
import fr.androidmakers.store.model.Venue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

/**
 *
 */
interface AndroidMakersStore {
    fun getVenue(id: String): Flow<Result<Venue>>
    fun getSpeaker(id: String): Flow<Result<Speaker>>
    fun getRoom(id: String): Flow<Result<Room>>
    fun getSession(id: String): Flow<Result<Session>>
    fun getPartners(): Flow<Result<List<Partner>>>
    fun getScheduleSlots(): Flow<Result<List<ScheduleSlot>>>
    fun getSessions(): Flow<Result<List<Session>>>
    fun getSpeakers(): Flow<Result<List<Speaker>>>
    fun getRooms(): Flow<Result<List<Room>>>

    fun getAgenda(): Flow<Result<Agenda>> {
        val sessionsFlow = getSessions()
        val slotsFlow = getScheduleSlots()
        val roomsFlow = getRooms()
        val speakersFlow = getSpeakers()

        return combine(
            sessionsFlow,
            slotsFlow,
            roomsFlow,
            speakersFlow
        ) { sessions, slots, rooms, speakers ->
            val exception = sessions.exceptionOrNull()
                ?: slots.exceptionOrNull()
                ?: rooms.exceptionOrNull()
                ?: speakers.exceptionOrNull()

            if (exception != null) {
                Result.failure(exception)
            } else {
                Result.success(
                    Agenda(
                        sessions.getOrThrow().associateBy { it.id },
                        slots.getOrThrow(),
                        rooms.getOrThrow().associateBy { it.id },
                        speakers.getOrThrow().associateBy { it.id }
                    )
                )
            }
        }
    }
}