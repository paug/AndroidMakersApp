package fr.androidmakers.di

import fr.androidmakers.domain.interactor.IosOpenMapUseCase
import fr.androidmakers.domain.interactor.IosShareSessionUseCase
import fr.androidmakers.domain.interactor.OpenMapUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import org.koin.dsl.module

actual val domainPlatformModule = module {
  factory<OpenMapUseCase> { IosOpenMapUseCase() }
  factory<ShareSessionUseCase> { IosShareSessionUseCase() }
}
