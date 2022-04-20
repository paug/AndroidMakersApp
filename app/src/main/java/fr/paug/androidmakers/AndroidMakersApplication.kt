package fr.paug.androidmakers

import android.app.Application
import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.firebase.FirebaseStore
import fr.paug.androidmakers.util.BookmarksStore
import io.openfeedback.android.OpenFeedback

class AndroidMakersApplication : Application() {
    lateinit var store: AndroidMakersStore
    lateinit var openFeedback: OpenFeedback

    override fun onCreate() {
        instance_ = this

        // Replace with proper dependency injection
        store = FirebaseStore()

        super.onCreate()
        BookmarksStore.init(this)

        openFeedback = OpenFeedback(context = this,
            openFeedbackProjectId = "am-2022",
            firebaseConfig = OpenFeedback.FirebaseConfig(
                projectId = "openfeedback-am-2022",
                applicationId = "1:378302699578:web:35d970e43cb3207eeebd03",
                apiKey = "AIzaSyAd3HgjDN3II5NrE97myBQkWNUrky3A70I",
                databaseUrl = "https://openfeedback-am-2022.firebaseio.com"
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