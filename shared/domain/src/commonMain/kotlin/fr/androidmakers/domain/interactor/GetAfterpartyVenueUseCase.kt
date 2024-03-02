package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.VenueRepository

class GetAfterpartyVenueUseCase(
    private val venueRepository: VenueRepository
) {
  operator fun invoke() = venueRepository.getVenue("afterparty")
}
