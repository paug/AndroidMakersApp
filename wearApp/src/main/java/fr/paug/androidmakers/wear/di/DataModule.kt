package fr.paug.androidmakers.wear.di

import fr.paug.androidmakers.wear.data.LocalPreferencesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
  singleOf(::LocalPreferencesRepository)
}
