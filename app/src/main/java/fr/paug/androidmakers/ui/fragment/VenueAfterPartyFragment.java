package fr.paug.androidmakers.ui.fragment;

import android.net.Uri;

import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Venue;

public class VenueAfterPartyFragment extends AbstractVenueFragment {

    private final Uri AFTER_PARTY_VENUE_COORDINATES_URI =
            Uri.parse("geo:" + getVenueInformation().coordinates +
                    "?q=" + Uri.encode(getVenueInformation().name));

    protected Uri getVenueCoordinatesUri() {
        return AFTER_PARTY_VENUE_COORDINATES_URI;
    }

    @Override
    protected Venue getVenueInformation() {
        Venue party = AgendaRepository.getInstance().getVenue(2);
        if (party != null) {
            return party;
        } else {
            return new Venue();
        }
    }

}