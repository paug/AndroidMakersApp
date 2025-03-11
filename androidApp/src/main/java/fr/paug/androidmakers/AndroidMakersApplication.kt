package fr.paug.androidmakers

import android.app.Application
import com.androidmakers.di.viewModelModule
import fr.androidmakers.di.DependenciesBuilder
import io.openfeedback.viewmodels.initializeOpenFeedback
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
