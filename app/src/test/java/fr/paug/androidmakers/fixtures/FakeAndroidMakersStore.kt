package fr.paug.androidmakers.fixtures

import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.model.Partner
import fr.androidmakers.store.model.Room
import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Session
import fr.androidmakers.store.model.Speaker
import fr.androidmakers.store.model.Venue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAndroidMakersStore : AndroidMakersStore {
  val roomsMutableFlow = MutableStateFlow(Result.success(emptyList<Room>()))

  override fun getRooms(): Flow<Result<List<Room>>> = roomsMutableFlow

  override fun getVenue(id: String): Flow<Result<Venue>> {
    TODO("Not yet implemented")
  }

  override fun getSpeaker(id: String): Flow<Result<Speaker>> {
    TODO("Not yet implemented")
  }

  override fun getRoom(id: String): Flow<Result<Room>> {
    TODO("Not yet implemented")
  }

  override fun getSession(id: String): Flow<Result<Session>> {
    TODO("Not yet implemented")
  }

  override fun getPartners(): Flow<Result<List<Partner>>> {
    TODO("Not yet implemented")
  }

  override fun getScheduleSlots(): Flow<Result<List<ScheduleSlot>>> {
    TODO("Not yet implemented")
  }

  override fun getSessions(): Flow<Result<List<Session>>> {
    TODO("Not yet implemented")
  }

  override fun getSpeakers(): Flow<Result<List<Speaker>>> {
    TODO("Not yet implemented")
  }

  override fun getBookmarks(userId: String): Flow<Result<Set<String>>> {
    TODO("Not yet implemented")
  }

  override suspend fun setBookmark(userId: String, sessionId: String, value: Boolean) {
    TODO("Not yet implemented")
  }
}