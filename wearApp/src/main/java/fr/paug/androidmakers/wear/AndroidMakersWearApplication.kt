package fr.paug.androidmakers.wear;

import android.app.Application
import fr.androidmakers.di.DependenciesBuilder
import fr.paug.androidmakers.wear.di.androidViewModelModule

class AndroidMakersWearApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    DependenciesBuilder(this).inject(
        listOf(androidViewModelModule)
    )
  }
}
