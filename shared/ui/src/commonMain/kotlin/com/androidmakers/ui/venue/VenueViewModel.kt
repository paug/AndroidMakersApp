package com.androidmakers.ui.venue

import androidx.lifecycle.ViewModel
import fr.androidmakers.domain.interactor.GetAfterpartyVenueUseCase
import fr.androidmakers.domain.interactor.GetConferenceVenueUseCase
import fr.androidmakers.domain.interactor.OpenMapUseCase

class VenueViewModel(
    val getConferenceVenueUseCase: GetConferenceVenueUseCase,
    val getAfterpartyVenueUseCase: GetAfterpartyVenueUseCase,
    val openMapUseCase: OpenMapUseCase,
): ViewModel() {
  // NOTHING for the moment
}
