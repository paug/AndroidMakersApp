package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.repo.SpeakersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSpeakersRepository : SpeakersRepository {
    val speakersFlow = MutableStateFlow<Result<List<Speaker>>>(Result.success(emptyList()))
    val speakerFlow = MutableStateFlow<Result<Speaker>>(Result.failure(NoSuchElementException()))

    override fun getSpeakers(refresh: Boolean): Flow<Result<List<Speaker>>> = speakersFlow
    override fun getSpeaker(id: String): Flow<Result<Speaker>> = speakerFlow
    override fun getSpeakers(ids: List<String>): Flow<Result<List<Speaker>>> = speakersFlow
}
