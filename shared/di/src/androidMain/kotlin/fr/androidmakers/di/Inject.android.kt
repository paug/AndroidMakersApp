package fr.androidmakers.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module


actual class DependenciesBuilder(private val context: Context) {
  actual fun inject(platformModules: List<Module>) {
    startKoin {
      androidLogger(Level.DEBUG)
      androidContext(context)
      modules(
          platformModules +
          sharedModules
      )
    }
  }
}
