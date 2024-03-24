package fr.paug.androidmakers.wear.di

import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import org.koin.dsl.module

val dataModule = module {
  single<LocalPreferencesRepository> { LocalPreferencesRepository(get()) }
}
