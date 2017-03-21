package fr.paug.androidmakers.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.ui.fragment.AboutFragment;
import fr.paug.androidmakers.ui.fragment.VenueFragment;

public class MainActivity extends AppCompatActivity {

    VenueFragment venueFragment = new VenueFragment();
    AboutFragment aboutFragment = new AboutFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_agenda:
                    agenda();
                    return true;
                case R.id.navigation_venue:
                    venue();
                    return true;
                case R.id.navigation_about:
                    about();
                    return true;
            }
            return false;
        }

    };

    private void agenda() {

    }

    private void venue() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, venueFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    private void about() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, aboutFragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
