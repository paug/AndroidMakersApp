package fr.paug.androidmakers.fixtures

import fr.androidmakers.domain.model.PartnerGroup
import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.model.Venue
import fr.androidmakers.domain.repo.PartnersRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.repo.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAndroidMakersStore : RoomsRepository, VenueRepository, SpeakersRepository,
  SessionsRepository, PartnersRepository {
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

  override fun getPartners(): Flow<Result<List<PartnerGroup>>> {
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
