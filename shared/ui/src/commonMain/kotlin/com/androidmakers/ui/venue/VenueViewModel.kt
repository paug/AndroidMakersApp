package com.androidmakers.ui.venue

import fr.androidmakers.domain.interactor.GetAfterpartyVenueUseCase
import fr.androidmakers.domain.interactor.GetConferenceVenueUseCase
import moe.tlaster.precompose.viewmodel.ViewModel


class VenueViewModel(
    val getConferenceVenueUseCase: GetConferenceVenueUseCase,
    val getAfterpartyVenueUseCase: GetAfterpartyVenueUseCase
): ViewModel() {
  // NOTHING for the moment
}
