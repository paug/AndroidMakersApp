package fr.paug.androidmakers;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.text.emoji.EmojiCompat;
import android.support.text.emoji.FontRequestEmojiCompatConfig;
import android.support.v4.provider.FontRequest;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.database.FirebaseDatabase;

import fr.paug.androidmakers.util.SessionSelector;
import io.fabric.sdk.android.Fabric;

public class AndroidMakersApplication extends Application {

    public static final String TAG = "AndroidMakersApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        SessionSelector.getInstance().init(this);

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