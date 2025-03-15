package fr.androidmakers.di

import fr.androidmakers.domain.interactor.OpenMapUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import fr.androidmakers.domain.utils.UrlOpener
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val domainPlatformModule = module {
  singleOf(::UrlOpener)

  factoryOf(::OpenMapUseCase)
  factoryOf(::ShareSessionUseCase)
}
