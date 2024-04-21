package fr.androidmakers.di

import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.MessagingRepository
import fr.androidmakers.domain.repo.PartnersRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.domain.repo.VenueRepository
import fr.androidmakers.store.firebase.FirebaseUserRepository
import fr.androidmakers.store.graphql.PartnersGraphQLRepository
import fr.androidmakers.store.graphql.RoomsGraphQLRepository
import fr.androidmakers.store.graphql.SessionsGraphQLRepository
import fr.androidmakers.store.graphql.SpeakersGraphQLRepository
import fr.androidmakers.store.graphql.VenueGraphQLRepository
import fr.androidmakers.store.local.BookmarksDataStoreRepository
import fr.androidmakers.store.wear.WearMessagingRepository
import org.koin.core.module.Module
import org.koin.dsl.module


expect val dataPlatformModule: Module

val dataModule = module {
  single<PartnersRepository> { PartnersGraphQLRepository(get()) }
  single<RoomsRepository> { RoomsGraphQLRepository(get()) }
  single<SessionsRepository> { SessionsGraphQLRepository(get()) }
  single<SpeakersRepository> { SpeakersGraphQLRepository(get()) }
  single<UserRepository> { FirebaseUserRepository() }
  single<VenueRepository> { VenueGraphQLRepository(get()) }

  single<BookmarksRepository> { BookmarksDataStoreRepository(get()) }
  single<MessagingRepository> { WearMessagingRepository(get()) }
}
