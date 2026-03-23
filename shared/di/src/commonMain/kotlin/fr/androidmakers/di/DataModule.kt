package fr.androidmakers.di

import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.repo.FeatureFlagsRepository
import fr.androidmakers.domain.repo.FeedRepository
import fr.androidmakers.domain.repo.MessagingRepository
import fr.androidmakers.domain.repo.PartnersRepository
import fr.androidmakers.domain.repo.RoomsRepository
import fr.androidmakers.domain.repo.SessionsRepository
import fr.androidmakers.domain.repo.SpeakersRepository
import fr.androidmakers.domain.repo.ThemeRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.androidmakers.domain.repo.VenueRepository
import fr.androidmakers.store.firebase.FirebaseUserRepository
import fr.androidmakers.store.graphql.FeatureFlagsGraphQLRepository
import fr.androidmakers.store.graphql.FeedGraphQLRepository
import fr.androidmakers.store.graphql.PartnersGraphQLRepository
import fr.androidmakers.store.graphql.RoomsGraphQLRepository
import fr.androidmakers.store.graphql.SessionsGraphQLRepository
import fr.androidmakers.store.graphql.SpeakersGraphQLRepository
import fr.androidmakers.store.graphql.VenueGraphQLRepository
import fr.androidmakers.store.local.BookmarksDataStoreRepository
import fr.androidmakers.store.local.ThemeDataStoreRepository
import fr.androidmakers.store.wear.WearMessagingRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val dataPlatformModule: Module

val dataModule = module {
  singleOf(::BookmarksDataStoreRepository) bind BookmarksRepository::class
  singleOf(::FeedGraphQLRepository) bind FeedRepository::class
  singleOf(::FirebaseUserRepository) bind UserRepository::class
  singleOf(::PartnersGraphQLRepository) bind PartnersRepository::class
  singleOf(::RoomsGraphQLRepository) bind RoomsRepository::class
  singleOf(::SessionsGraphQLRepository) bind SessionsRepository::class
  singleOf(::SpeakersGraphQLRepository) bind SpeakersRepository::class
  singleOf(::ThemeDataStoreRepository) bind ThemeRepository::class
  singleOf(::VenueGraphQLRepository) bind VenueRepository::class
  singleOf(::WearMessagingRepository) bind MessagingRepository::class
  singleOf(::FeatureFlagsGraphQLRepository) bind FeatureFlagsRepository::class
}
