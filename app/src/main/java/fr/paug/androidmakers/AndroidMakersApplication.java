package fr.paug.androidmakers;

import android.app.Application;
import androidx.annotation.Nullable;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
import androidx.core.provider.FontRequest;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import fr.paug.androidmakers.util.SessionSelector;
import io.fabric.sdk.android.Fabric;

public class AndroidMakersApplication extends Application {

    public static final String TAG = "AndroidMakersApp";

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        SessionSelector.Companion.getInstance().init(this);

        // Use a downloadable font for EmojiCompat
        setupEmojiCompat();
    }

    private void setupEmojiCompat() {
        final FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs);
        final EmojiCompat.Config config = new FontRequestEmojiCompatConfig(getApplicationContext(), fontRequest)
                .setReplaceAll(true)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                        if (BuildConfig.DEBUG) {
                            Log.i(TAG, "EmojiCompat initialized");
                        }
                    }

                    @Override
                    public void onFailed(@Nullable Throwable throwable) {
                        Log.e(TAG, "EmojiCompat initialization failed", throwable);
                    }
                });

        EmojiCompat.init(config);
    }

}