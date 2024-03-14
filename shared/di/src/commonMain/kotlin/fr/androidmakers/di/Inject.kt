package fr.androidmakers.di

import org.koin.core.module.Module

expect class DependenciesBuilder {
  fun inject(platformModules: List<Module>)
}

val sharedModules = listOf(
    dataModule,
    domainModule,
    dataPlatformModule,
    domainPlatformModule
)
