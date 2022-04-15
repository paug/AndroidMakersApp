package fr.androidmakers.store

import fr.androidmakers.store.model.*
import kotlinx.coroutines.flow.*

/**
 *
 */
interface AndroidMakersStore {
    fun getVenue(id: String): Flow<Venue>
    fun getSpeaker(id: String): Flow<Speaker>
    fun getRoom(id: String): Flow<Room>
    fun getSession(id: String): Flow<Session>
    fun getPartners(): Flow<List<Partner>>
    fun getScheduleSlots(): Flow<List<ScheduleSlot>>
    fun getSessions(): Flow<List<Session>>
    fun getSpeakers(): Flow<List<Speaker>>
    fun getRooms(): Flow<List<Room>>

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
            Agenda(sessions.associateBy { it.id }, slots, rooms.associateBy { it.id }, speakers.associateBy { it.id })
        }.map {
            Result.success(it)
        }.catch {
            Result.failure<Agenda>(it)
        }
    }
}