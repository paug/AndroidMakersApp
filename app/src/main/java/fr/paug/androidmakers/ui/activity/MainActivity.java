package fr.paug.androidmakers.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.ScheduleSlot;
import fr.paug.androidmakers.ui.fragment.AboutFragment;
import fr.paug.androidmakers.ui.fragment.AgendaFragment;
import fr.paug.androidmakers.ui.fragment.MakerDroidFragment;
import fr.paug.androidmakers.ui.fragment.VenuePagerFragment;

public class MainActivity extends BaseActivity {

    private static final String TAG_FRAGMENT_AGENDA = "TAG_FRAGMENT_AGENDA";
    private static final String TAG_FRAGMENT_VENUE = "TAG_FRAGMENT_VENUE";
    private static final String TAG_FRAGMENT_ABOUT = "TAG_FRAGMENT_ABOUT";
    private static final String TAG_FRAGMENT_MAKERDROID = "TAG_FRAGMENT_MAKERDROID";

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation;

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
                case R.id.navigation_makerdroid:
                    tag = TAG_FRAGMENT_MAKERDROID;
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

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        handleURLLink(getIntent());
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
            case TAG_FRAGMENT_MAKERDROID:
                return new MakerDroidFragment();
            case TAG_FRAGMENT_AGENDA:
            default:
                return new AgendaFragment();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleURLLink(intent);
    }

    private void handleURLLink(@NonNull final Intent intent) {
        // Do not handle the Deep link if user is coming from recent tasks
        if (intent.getFlags() != (Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)) {
            final Uri data = intent.getData();
            if (data != null && data.getHost().equalsIgnoreCase("androidmakers.fr")) {
                final List<String> pathSegments = data.getPathSegments();
                if (pathSegments != null && pathSegments.size() > 0) {
                    final String path = pathSegments.get(0);
                    if (path.equalsIgnoreCase("schedule")) {
                        handleScheduleLink(data);
                    } else if (path.equalsIgnoreCase("logistics")) {
                        handleLogisticsLink();
                    }
                }
            }
        }
    }

    private void handleLogisticsLink() {
        if (navigation != null) {
            navigation.setSelectedItemId(R.id.navigation_venue);
        }
    }

    private void handleScheduleLink(@NonNull final Uri data) {
        if (navigation != null) {
            navigation.setSelectedItemId(R.id.navigation_agenda);
        }
        final String uriFragment = data.getFragment();
        if (uriFragment != null && uriFragment.startsWith("session-")) {
            final String[] split = uriFragment.split("session-");
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