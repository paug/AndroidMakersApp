package fr.paug.androidmakers.wear;

import android.app.Application
import android.content.Context
import fr.androidmakers.di.DependenciesBuilder
import fr.paug.androidmakers.wear.di.androidViewModelModule
import fr.paug.androidmakers.wear.di.dataModule

lateinit var applicationContext: Context

class AndroidMakersWearApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    fr.paug.androidmakers.wear.applicationContext = applicationContext
    DependenciesBuilder(this).inject(
        listOf(
            androidViewModelModule,
            dataModule,
        )
    )
  }
}
