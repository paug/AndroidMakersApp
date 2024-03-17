package fr.androidmakers.di

import fr.androidmakers.domain.interactor.OpenMapUseCase
import fr.androidmakers.domain.utils.UrlOpener
import org.koin.dsl.module

actual val domainPlatformModule = module {
  single {
    UrlOpener()
  }

  factory { OpenMapUseCase() }
}
