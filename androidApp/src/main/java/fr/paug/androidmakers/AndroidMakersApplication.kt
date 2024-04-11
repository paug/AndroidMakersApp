package fr.paug.androidmakers

import android.app.Application
import com.androidmakers.di.viewModelModule
import fr.androidmakers.di.DependenciesBuilder

class AndroidMakersApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    DependenciesBuilder(this).inject(
      listOf(
        viewModelModule,
      )
    )
  }
}

