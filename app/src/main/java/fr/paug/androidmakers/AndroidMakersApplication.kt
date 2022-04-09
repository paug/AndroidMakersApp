package fr.paug.androidmakers

import android.app.Application
import android.util.Log
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.EmojiCompat.InitCallback
import androidx.emoji.text.FontRequestEmojiCompatConfig
import fr.androidmakers.store.AndroidMakersStore
import fr.androidmakers.store.firebase.FirebaseStore
import fr.androidmakers.store.graphql.GraphQLStore
import fr.paug.androidmakers.util.SessionSelector

class AndroidMakersApplication : Application() {
    lateinit var store: AndroidMakersStore

    override fun onCreate() {
        instance_ = this

        // Replace with proper dependency injection
        store = GraphQLStore(this)

        super.onCreate()
        SessionSelector.init(this)
        // Use a downloadable font for EmojiCompat
        setupEmojiCompat()
    }

    private fun setupEmojiCompat() {
        val fontRequest = FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs)
        val config = FontRequestEmojiCompatConfig(applicationContext, fontRequest)
                .setReplaceAll(true)
                .registerInitCallback(object : InitCallback() {
                    override fun onInitialized() {
                        if (BuildConfig.DEBUG) {
                            Log.i(TAG, "EmojiCompat initialized")
                        }
                    }

                    override fun onFailed(throwable: Throwable?) {
                        Log.e(TAG, "EmojiCompat initialization failed", throwable)
                    }
                })
        EmojiCompat.init(config)
    }

    companion object {
        const val TAG = "AndroidMakersApp"
        private var instance_: AndroidMakersApplication? = null
        fun instance(): AndroidMakersApplication {
            return instance_!!
        }
    }
}