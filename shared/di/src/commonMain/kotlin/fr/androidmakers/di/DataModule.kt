package fr.androidmakers.di

import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.FeatureFlagsRepository
import fr.androidmakers.domain.repo.FeedRepository
import fr.androidmakers.domain.repo.MessagingRepository
import fr.androidmakers.domain.repo.PartnersRepository
import fr.androidmakers.domain.repo.ReviewRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.repo.ThemeRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.domain.repo.VenueRepository
import fr.androidmakers.store.firebase.FirebaseUserRepository
import fr.androidmakers.store.graphql.FeatureFlagsGraphQLRepository
import fr.androidmakers.store.graphql.PartnersGraphQLRepository
import fr.androidmakers.store.graphql.ReviewGraphQLRepository
import fr.androidmakers.store.graphql.RoomsGraphQLRepository
import fr.androidmakers.store.graphql.SessionsGraphQLRepository
import fr.androidmakers.store.graphql.SpeakersGraphQLRepository
import fr.androidmakers.store.graphql.VenueGraphQLRepository
import fr.androidmakers.store.local.BookmarksDataStoreRepository
import fr.androidmakers.store.local.ThemeDataStoreRepository
import fr.androidmakers.store.mock.MockFeedRepository
import fr.androidmakers.store.wear.WearMessagingRepository
import org.koin.core.module.Module
import org.koin.dsl.module


expect val dataPlatformModule: Module

val dataModule = module {
  single<PartnersRepository> { PartnersGraphQLRepository(get()) }
  single<RoomsRepository> { RoomsGraphQLRepository(get()) }
  single<SessionsRepository> { SessionsGraphQLRepository(get()) }
  single<SpeakersRepository> { SpeakersGraphQLRepository(get()) }
  single<UserRepository> { FirebaseUserRepository(get()) }
  single<VenueRepository> { VenueGraphQLRepository(get()) }
  single<FeatureFlagsRepository> { FeatureFlagsGraphQLRepository(get()) }
  single<ReviewRepository> { ReviewGraphQLRepository(get()) }

  single<BookmarksRepository> { BookmarksDataStoreRepository(get()) }
  single<ThemeRepository> { ThemeDataStoreRepository(get()) }
  single<MessagingRepository> { WearMessagingRepository(get()) }
  single<FeedRepository> { MockFeedRepository() }
}
