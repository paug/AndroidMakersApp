package fr.paug.androidmakers.ui.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MenuItem;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.ui.fragment.AboutFragment;
import fr.paug.androidmakers.ui.fragment.AgendaFragment;
import fr.paug.androidmakers.ui.fragment.VenuePagerFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG_FRAGMENT_AGENDA = "TAG_FRAGMENT_AGENDA";
    private static final String TAG_FRAGMENT_VENUE = "TAG_FRAGMENT_VENUE";
    private static final String TAG_FRAGMENT_ABOUT = "TAG_FRAGMENT_ABOUT";

    private Fragment fragment;
    private FragmentManager fragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String tag;

            switch (item.getItemId()) {
                case R.id.navigation_agenda:
                    tag = TAG_FRAGMENT_AGENDA;
                    break;
                case R.id.navigation_venue:
                    tag = TAG_FRAGMENT_VENUE;
                    break;
                case R.id.navigation_about:
                    tag = TAG_FRAGMENT_ABOUT;
                    break;
                default:
                    return false;
            }

            fragment = fragmentManager.findFragmentByTag(tag);

            if (fragment == null)
                fragment = newFragmentByTag(tag);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragment, tag);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            addAgenda();
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        handleURLLinks();
    }

    private void addAgenda() {
        fragment = new AgendaFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, fragment, TAG_FRAGMENT_AGENDA).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @NonNull
    private Fragment newFragmentByTag(@NonNull String tag) {
        switch (tag) {
            case TAG_FRAGMENT_VENUE:
                return new VenuePagerFragment();
            case TAG_FRAGMENT_ABOUT:
                return new AboutFragment();
            case TAG_FRAGMENT_AGENDA:
            default:
                return new AgendaFragment();
        }
    }

    public void handleURLLinks() {
        final Uri data = getIntent().getData();
        if (data != null && data.getHost().equalsIgnoreCase("androidmakers.fr")) {
            final String path = data.getPath();
            if (path != null && path.equalsIgnoreCase("/schedule/")) {
                final String uriFragment = data.getFragment();
                if (uriFragment.startsWith("session-")) {
                    String[] split = uriFragment.split("session-");
                    if (split.length > 1 && TextUtils.isDigitsOnly(split[1])) {
                        final Integer sessionId = Integer.valueOf(split[1]);
                        for (final ScheduleSlot scheduleSlot : AgendaRepository.getInstance().getScheduleSlots()) {
                            if (scheduleSlot.sessionId == sessionId) {
                                SessionDetailActivity.startActivity(this, sessionId, scheduleSlot.startDate, scheduleSlot.endDate, scheduleSlot.room);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

}