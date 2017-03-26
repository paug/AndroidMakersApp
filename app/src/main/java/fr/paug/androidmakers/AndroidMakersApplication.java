package fr.paug.androidmakers;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import fr.paug.androidmakers.util.SessionSelector;

/**
 * Created by stan on 18/03/2017.
 */
public class AndroidMakersApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        SessionSelector.getInstance().init(this);
    }

}