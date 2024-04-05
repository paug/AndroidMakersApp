package fr.paug.androidmakers

import android.app.Application
import com.androidmakers.di.uiModule
import com.androidmakers.di.viewModelModule
import com.androidmakers.utils.NotificationUtils
import fr.androidmakers.di.DependenciesBuilder
import fr.paug.androidmakers.di.androidViewModelModule

class AndroidMakersApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    NotificationUtils(this@AndroidMakersApplication).initNotifications()

    DependenciesBuilder(this).inject(
      listOf(
        androidViewModelModule,
        viewModelModule,
        uiModule
      )
    )
  }
}
