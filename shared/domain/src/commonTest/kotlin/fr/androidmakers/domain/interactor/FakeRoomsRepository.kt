package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.repo.RoomsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeRoomsRepository : RoomsRepository {
    val roomsFlow = MutableStateFlow<Result<List<Room>>>(Result.success(emptyList()))
    val roomFlow = MutableStateFlow<Result<Room>>(Result.failure(NoSuchElementException()))

    override fun getRoom(id: String): Flow<Result<Room>> = roomFlow
    override fun getRooms(refresh: Boolean): Flow<Result<List<Room>>> = roomsFlow
}
