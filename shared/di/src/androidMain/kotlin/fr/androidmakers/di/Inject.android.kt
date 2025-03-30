package fr.androidmakers.di

import android.content.Context
import fr.paug.androidmakers.di.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class DependenciesBuilder(private val context: Context) {
  fun inject(platformModules: List<Module>) {
    startKoin {
      if (BuildConfig.DEBUG) {
        androidLogger(Level.DEBUG)
      }
      androidContext(context)
      modules(
          platformModules +
          sharedModules
      )
    }
  }
}
