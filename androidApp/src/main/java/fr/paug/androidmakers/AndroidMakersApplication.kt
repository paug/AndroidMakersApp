package fr.paug.androidmakers

import android.app.Application
import com.androidmakers.di.viewModelModule
import com.androidmakers.ui.LocalPlatformContext
import fr.androidmakers.di.DependenciesBuilder
import io.openfeedback.m3.initializeOpenFeedback
import io.openfeedback.viewmodels.OpenFeedbackFirebaseConfig

class AndroidMakersApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    initializeOpenFeedback(OpenFeedbackFirebaseConfig.default(this))
    DependenciesBuilder(this).inject(
      listOf(
        viewModelModule,
      )
    )
  }
}

