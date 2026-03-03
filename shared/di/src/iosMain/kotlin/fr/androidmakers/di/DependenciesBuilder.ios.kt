package fr.androidmakers.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

class DependenciesBuilder {
  fun inject(platformModules: List<Module>) {
    startKoin {
      modules(
          platformModules +
          sharedModules
      )
    }
  }
}
