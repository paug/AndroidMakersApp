package fr.paug.androidmakers.ui.fragment;

import android.net.Uri;

import fr.paug.androidmakers.R;
import fr.paug.androidmakers.model.VenueInformations;

public class VenueConferenceFragment extends AbstractVenueFragment {

    private static final String CONFERENCE_VENUE_COORDINATES_URI = "geo:48.834851, 2.386445?q=" + Uri.encode("Les Salons De L'aveyron");

    protected Uri getVenueCoordinatesUri() {
        return Uri.parse(CONFERENCE_VENUE_COORDINATES_URI);
    }

    @Override
    protected VenueInformations getVenueInformations() {
        return new VenueInformations(getString(R.string.venue_conference_title), getString(R.string.venue_conference_address), getString(R.string.venue_conference_directions), null);
    }
}