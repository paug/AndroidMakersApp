package fr.paug.androidmakers.wear

import android.app.Application
import fr.androidmakers.di.DependenciesBuilder
import fr.paug.androidmakers.wear.di.dataModule
import fr.paug.androidmakers.wear.di.messagingModule
import fr.paug.androidmakers.wear.di.signInModule
import fr.paug.androidmakers.wear.di.viewModelModule

class AndroidMakersWearApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    DependenciesBuilder(this).inject(
      listOf(
        viewModelModule,
        dataModule,
        signInModule,
        messagingModule
      )
    )
  }
}
