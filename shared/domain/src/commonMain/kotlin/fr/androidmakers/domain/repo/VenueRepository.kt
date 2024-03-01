package fr.androidmakers.domain.repo

import at.asitplus.KmmResult
import fr.androidmakers.domain.model.Venue
import kotlinx.coroutines.flow.Flow

interface VenueRepository {
  fun getVenue(id: String): Flow<KmmResult<Venue>>
}
