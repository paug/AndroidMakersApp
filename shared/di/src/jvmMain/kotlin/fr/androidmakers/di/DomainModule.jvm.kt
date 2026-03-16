package fr.androidmakers.di

import fr.androidmakers.domain.interactor.DesktopOpenMapUseCase
import fr.androidmakers.domain.interactor.DesktopShareSessionUseCase
import fr.androidmakers.domain.interactor.OpenMapUseCase
import fr.androidmakers.domain.interactor.ShareSessionUseCase
import org.koin.dsl.module

actual val domainPlatformModule = module {
  factory<OpenMapUseCase> { DesktopOpenMapUseCase() }
  factory<ShareSessionUseCase> { DesktopShareSessionUseCase() }
}
