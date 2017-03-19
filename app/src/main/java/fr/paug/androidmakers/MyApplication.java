package fr.paug.androidmakers;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by stan on 18/03/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
