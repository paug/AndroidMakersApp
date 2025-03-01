package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {

  fun getRoom(id: String): Flow<Result<Room>>

  fun getRooms(refresh: Boolean): Flow<Result<List<Room>>>
}
