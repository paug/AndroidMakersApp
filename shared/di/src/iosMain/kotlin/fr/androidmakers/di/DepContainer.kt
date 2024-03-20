package fr.androidmakers.di

import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenPartnerLinkUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DepContainer: KoinComponent {

  val setSessionBookmarkedUseCase: SetSessionBookmarkUseCase by inject()
  val getFavoritesSessionsUseCase: GetFavoriteSessionsUseCase by inject()
  val openPartnerLinkUseCase: OpenPartnerLinkUseCase by inject()
}
