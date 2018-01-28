package fr.paug.androidmakers.ui.fragment;

import android.net.Uri;

import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Venue;

public class VenueAfterPartyFragment extends AbstractVenueFragment {

    private final Uri AFTER_PARTY_VENUE_COORDINATES_URI =
            Uri.parse("geo:" + getVenueInformations().coordinates + "?q=" + Uri.encode(getVenueInformations().name));

    protected Uri getVenueCoordinatesUri() {
        return AFTER_PARTY_VENUE_COORDINATES_URI;
    }

    @Override
    protected Venue getVenueInformations() {
        return AgendaRepository.getInstance().getVenue(2);
    }

}