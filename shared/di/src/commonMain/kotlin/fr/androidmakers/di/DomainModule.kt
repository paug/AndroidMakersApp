package fr.androidmakers.di

import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.GetAfterpartyVenueUseCase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetConferenceVenueUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.GetPartnersUseCase
import fr.androidmakers.domain.interactor.MergeBookmarksUseCase
import fr.androidmakers.domain.interactor.OpenBlueskyAccountUseCase
import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenLinkUseCase
import fr.androidmakers.domain.interactor.OpenPartnerLinkUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

expect val domainPlatformModule: Module

val domainModule = module {
  factoryOf(::GetAgendaUseCase)
  factoryOf(::GetConferenceVenueUseCase)
  factoryOf(::GetAfterpartyVenueUseCase)
  factoryOf(::MergeBookmarksUseCase)
  factoryOf(::OpenFaqUseCase)
  factoryOf(::OpenCocUseCase)
  factoryOf(::OpenYoutubeUseCase)
  factoryOf(::OpenXHashtagUseCase)
  factoryOf(::OpenBlueskyAccountUseCase)
  factoryOf(::OpenXAccountUseCase)
  factoryOf(::SetSessionBookmarkUseCase)
  factoryOf(::GetPartnersUseCase)
  factoryOf(::GetFavoriteSessionsUseCase)
  factoryOf(::OpenPartnerLinkUseCase)
  factoryOf(::OpenLinkUseCase)
  factoryOf(::ApplyForAppClinicUseCase)
}
