package fr.paug.androidmakers

import android.app.Application
import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.graphql.GraphQLStore
import fr.paug.androidmakers.util.BookmarksStore
import io.openfeedback.android.OpenFeedback


class AndroidMakersApplication : Application() {
  lateinit var store: AndroidMakersStore
  lateinit var openFeedback: OpenFeedback

  override fun onCreate() {
    instance_ = this

    store = GraphQLStore(this)

    super.onCreate()
    BookmarksStore.init(this)

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