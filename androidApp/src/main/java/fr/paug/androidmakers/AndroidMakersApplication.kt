package fr.paug.androidmakers

import android.app.Application
import fr.androidmakers.di.DependenciesBuilder
import fr.androidmakers.domain.interactor.GetAfterpartyVenueUseCase
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import fr.androidmakers.domain.interactor.GetConferenceVenueUseCase
import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase
import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import fr.androidmakers.domain.repo.BookmarksRepository
import fr.androidmakers.domain.utils.UrlOpener
import fr.androidmakers.store.local.createDataStore
import fr.androidmakers.store.local.BookmarksDataStoreRepository
import fr.paug.androidmakers.di.viewModelModule
import io.openfeedback.android.OpenFeedback



class AndroidMakersApplication : Application() {

  lateinit var urlOpener: UrlOpener

  lateinit var openFeedback: OpenFeedback

  override fun onCreate() {
    instance_ = this
    super.onCreate()

    DependenciesBuilder(this).inject(
        listOf(viewModelModule)
    )

    urlOpener = UrlOpener(this)

    openFeedback = OpenFeedback(
        context = this,
        openFeedbackProjectId = "am2023",
        firebaseConfig = OpenFeedback.FirebaseConfig(
            projectId = "open-feedback-42",
            applicationId = "1:635903227116:web:31de912f8bf29befb1e1c9",
            apiKey = "AIzaSyB3ELJsaiItrln0uDGSuuHE1CfOJO67Hb4",
            databaseUrl = "https://open-feedback-42.firebaseio.com"
        )
    )
  }

  companion object {
    private var instance_: AndroidMakersApplication? = null
    fun instance(): AndroidMakersApplication {
      return instance_!!
    }
  }
}
