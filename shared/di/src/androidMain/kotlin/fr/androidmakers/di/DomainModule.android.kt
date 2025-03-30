package fr.androidmakers.di

import fr.androidmakers.domain.interactor.OpenMapUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

actual val domainPlatformModule = module {
  factoryOf(::OpenMapUseCase)
  factoryOf(::ShareSessionUseCase)
}
