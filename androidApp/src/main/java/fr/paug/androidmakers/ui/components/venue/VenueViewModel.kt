package fr.paug.androidmakers.ui.components.venue

import androidx.lifecycle.ViewModel
import fr.androidmakers.domain.interactor.GetAfterpartyVenueUseCase
import fr.androidmakers.domain.interactor.GetConferenceVenueUseCase

class VenueViewModel(
    val getConferenceVenueUseCase: GetConferenceVenueUseCase,
    val getAfterpartyVenueUseCase: GetAfterpartyVenueUseCase
): ViewModel() {
  // NOTHING for the moment
}
