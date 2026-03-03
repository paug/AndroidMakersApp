package fr.androidmakers.di

import android.content.Context
import android.content.pm.ApplicationInfo
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

class DependenciesBuilder(private val context: Context) {
  fun inject(platformModules: List<Module>) {
    startKoin {
      val isDebuggable = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
      if (isDebuggable) {
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
