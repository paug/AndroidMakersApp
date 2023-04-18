package fr.androidmakers.store

import fr.androidmakers.store.model.*
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
  fun getSessions(): Flow<Result<List<Session>>>
  fun getSpeakers(): Flow<Result<List<Speaker>>>
  fun getRooms(): Flow<Result<List<Room>>>
  fun getBookmarks(userId: String): Flow<Result<Set<String>>>
  suspend fun setBookmark(userId: String, sessionId: String, value: Boolean)
}

fun AndroidMakersStore.getAgenda(): Flow<Result<Agenda>> {
  return combine(
      getSessions(),
      getRooms(),
      getSpeakers(),
  ) { sessions, rooms, speakers ->


    sessions.exceptionOrNull()?.let {
      it.printStackTrace()
      return@combine Result.failure(it)
    }
    rooms.exceptionOrNull()?.let {
      it.printStackTrace()
      return@combine Result.failure(it)
    }
    speakers.exceptionOrNull()?.let {
      it.printStackTrace()
      return@combine Result.failure(it)
    }

    Result.success(
        Agenda(
            sessions = sessions.getOrThrow().associateBy { it.id },
            rooms = rooms.getOrThrow().associateBy { it.id },
            speakers = speakers.getOrThrow().associateBy { it.id }
        )
    )
  }
}