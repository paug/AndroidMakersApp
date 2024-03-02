package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.VenueRepository

class GetConferenceVenueUseCase(
    private val venueRepository: VenueRepository
) {
  operator fun invoke() = venueRepository.getVenue("conference")
}
