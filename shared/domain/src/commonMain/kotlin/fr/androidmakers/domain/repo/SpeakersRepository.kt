package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.Speaker
import kotlinx.coroutines.flow.Flow

interface SpeakersRepository {

  fun getSpeaker(id: String): Flow<Result<Speaker>>
  fun getSpeakers(): Flow<Result<List<Speaker>>>
}
