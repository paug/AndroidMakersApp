package fr.androidmakers.domain.repo

import at.asitplus.KmmResult
import fr.androidmakers.domain.model.Room
import kotlinx.coroutines.flow.Flow

interface RoomsRepository {

  fun getRoom(id: String): Flow<KmmResult<Room>>

  fun getRooms(): Flow<KmmResult<List<Room>>>
}
