package fr.androidmakers.di

import fr.androidmakers.domain.interactor.GetAfterpartyVenueUseCase
import fr.androidmakers.domain.interactor.GetConferenceVenueUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.GetPartnersUseCase
import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenPartnerLinkUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.repo.SpeakersRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DepContainer: KoinComponent {
  val getConferenceVenueUseCase: GetConferenceVenueUseCase by inject()
  val getAfterpartyVenueUseCase: GetAfterpartyVenueUseCase by inject()

  val openXAccountUseCase: OpenXAccountUseCase by inject()
  val openXHashtagUseCase: OpenXHashtagUseCase by inject()
  val openYoutubeUseCase: OpenYoutubeUseCase by inject()
  val openFaqUseCase: OpenFaqUseCase by inject()
  val openCocUseCase: OpenCocUseCase by inject()

  val speakersRepository: SpeakersRepository by inject()

  val getPartnersUseCase: GetPartnersUseCase by inject()
  val setSessionBookmarkedUseCase: SetSessionBookmarkUseCase by inject()
  val getFavoritesSessionsUseCase: GetFavoriteSessionsUseCase by inject()
  val openPartnerLinkUseCase: OpenPartnerLinkUseCase by inject()
}
