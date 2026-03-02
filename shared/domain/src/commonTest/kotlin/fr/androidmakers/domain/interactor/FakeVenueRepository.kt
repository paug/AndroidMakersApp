package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.model.Venue
import fr.androidmakers.domain.repo.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeVenueRepository : VenueRepository {
    val conferenceFlow = MutableStateFlow<Result<Venue>>(Result.failure(NoSuchElementException()))
    val afterpartyFlow = MutableStateFlow<Result<Venue>>(Result.failure(NoSuchElementException()))

    override fun getVenue(id: String): Flow<Result<Venue>> = when (id) {
        "conference" -> conferenceFlow
        "afterparty" -> afterpartyFlow
        else -> MutableStateFlow(Result.failure(NoSuchElementException("Unknown venue: $id")))
    }
}
