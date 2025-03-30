package fr.androidmakers.di

import fr.androidmakers.domain.interactor.AndroidOpenMapUseCase
import fr.androidmakers.domain.interactor.AndroidShareSessionUseCase
import fr.androidmakers.domain.interactor.OpenMapUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import org.koin.dsl.module

actual val domainPlatformModule = module {
  factory<OpenMapUseCase> { AndroidOpenMapUseCase() }
  factory<ShareSessionUseCase> { AndroidShareSessionUseCase() }
}
