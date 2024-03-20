package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.Venue
import kotlinx.coroutines.flow.Flow

interface VenueRepository {
  fun getVenue(id: String): Flow<Result<Venue>>
}
