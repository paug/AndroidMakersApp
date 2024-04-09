package fr.androidmakers.di

import fr.androidmakers.domain.interactor.ApplyForAppClinicUseCase
import fr.androidmakers.domain.interactor.GetAfterpartyVenueUseCase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetConferenceVenueUseCase
import fr.androidmakers.domain.interactor.GetFavoriteSessionsUseCase
import fr.androidmakers.domain.interactor.GetPartnersUseCase
import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenLinkUseCase
import fr.androidmakers.domain.interactor.OpenMapUseCase
import fr.androidmakers.domain.interactor.OpenPartnerLinkUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase
import fr.androidmakers.domain.interactor.SetSessionBookmarkUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

expect val domainPlatformModule: Module

val domainModule = module {
  factory { GetAgendaUseCase(get(), get(), get()) }
  factory { GetConferenceVenueUseCase(get()) }
  factory { GetAfterpartyVenueUseCase(get()) }
  factory { SyncBookmarksUseCase(get(), get()) }
  factory { OpenFaqUseCase(get()) }
  factory { OpenCocUseCase(get()) }
  factory { OpenYoutubeUseCase(get()) }
  factory { OpenXHashtagUseCase(get()) }
  factory { OpenXAccountUseCase(get()) }
  factory { SetSessionBookmarkUseCase(get(), get(), get()) }
  factory { GetPartnersUseCase(get()) }
  factory { GetFavoriteSessionsUseCase(get()) }
  factory { OpenPartnerLinkUseCase(get()) }
  factory { OpenLinkUseCase(get()) }
  factory { ApplyForAppClinicUseCase(get()) }
}
