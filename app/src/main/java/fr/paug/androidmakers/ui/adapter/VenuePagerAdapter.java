package fr.paug.androidmakers.ui.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.ui.fragment.VenueAfterPartyFragment;
import fr.paug.androidmakers.ui.fragment.VenueConferenceFragment;
import fr.paug.androidmakers.ui.fragment.VenueFloorPlanFragment;

public class VenuePagerAdapter extends FragmentPagerAdapter {

    private Context context;

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return context.getString(R.string.venue_conference_tab);
            case 1:
                return context.getString(R.string.venue_afterparty_tab);
            case 2:
                return context.getString(R.string.venue_floor_plan_tab);
            default:
                return super.getPageTitle(position);
        }
    }

    public VenuePagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        this.context = ctx;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new VenueConferenceFragment();
            case 1:
                return new VenueAfterPartyFragment();
            case 2:
                return new VenueFloorPlanFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}