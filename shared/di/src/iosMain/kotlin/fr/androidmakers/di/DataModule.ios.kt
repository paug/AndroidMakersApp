package fr.androidmakers.di

import fr.androidmakers.store.graphql.ApolloClientBuilder
import org.koin.dsl.module

actual val dataPlatformModule = module {
  single { ApolloClientBuilder("https://androidmakers-2023.ew.r.appspot.com/graphql", "androidmakers2023", "") }
}
