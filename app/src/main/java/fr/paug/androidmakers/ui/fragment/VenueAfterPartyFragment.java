package fr.paug.androidmakers.ui.fragment;

import android.net.Uri;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.model.VenueInformations;

/**
 * Created by benju on 30/03/2017.
 */

public class VenueAfterPartyFragment extends AbstractVenueFragment {

    private static final Uri AFTER_PARTY_VENUE_COORDINATES_URI = Uri.parse("geo:48.833057, 2.386048?q=" + Uri.encode("The Frog at Bercy Village"));

    protected Uri getVenueCoordinatesUri() {
        return AFTER_PARTY_VENUE_COORDINATES_URI;
    }

    @Override
    protected VenueInformations getVenueInformations() {
        return new VenueInformations(getString(R.string.venue_afterparty_title), getString(R.string.venue_afterparty_address), getString(R.string.venue_afterparty_info), null);
    }
}