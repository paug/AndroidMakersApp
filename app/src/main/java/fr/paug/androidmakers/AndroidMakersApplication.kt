package fr.paug.androidmakers

import android.app.Application
import android.util.Log
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.EmojiCompat.InitCallback
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.crashlytics.android.Crashlytics
import fr.paug.androidmakers.util.SessionSelector.init
import io.fabric.sdk.android.Fabric
import io.openfeedback.android.OpenFeedback

class AndroidMakersApplication : Application() {
    lateinit var openFeedback: OpenFeedback

    override fun onCreate() {
        instance_ = this
        super.onCreate()
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
        init(this)
        // Use a downloadable font for EmojiCompat
        setupEmojiCompat()

        openFeedback = OpenFeedback(this,
                openFeedbackProjectId = "7Hq01JIxGJtCQ7bRGIYN",
                firebaseConfig = OpenFeedback.FirebaseConfig(
                        applicationId = BuildConfig.OPENFEEDBACK_APPLICATION_ID,
                        projectId = BuildConfig.OPENFEEDBACK_PROJECT_ID,
                        apiKey = BuildConfig.OPENFEEDBACK_API_KEY,
                        databaseUrl = "https://${BuildConfig.OPENFEEDBACK_PROJECT_ID}.firebaseio.com"
                )
        )
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