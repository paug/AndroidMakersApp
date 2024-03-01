package fr.androidmakers.domain.repo

import at.asitplus.KmmResult
import fr.androidmakers.domain.model.Speaker
import kotlinx.coroutines.flow.Flow

interface SpeakersRepository {

  fun getSpeaker(id: String): Flow<KmmResult<Speaker>>

  fun getSpeakers(): Flow<KmmResult<List<Speaker>>>
}
