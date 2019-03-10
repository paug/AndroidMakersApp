package fr.paug.androidmakers.ui.fragment;

import android.net.Uri;

import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Venue;

public class VenueAfterPartyFragment extends AbstractVenueFragment {

    private final Uri AFTER_PARTY_VENUE_COORDINATES_URI =
            Uri.parse("geo:" + getVenueInformation().getCoordinates() +
                    "?q=" + Uri.encode(getVenueInformation().getName()));

    protected Uri getVenueCoordinatesUri() {
        return AFTER_PARTY_VENUE_COORDINATES_URI;
    }

    @Override
    public Venue getVenueInformation() {
        Venue party = AgendaRepository.getInstance().getVenue(2);
        if (party != null) {
            return party;
        } else {
            return new Venue();
        }
    }

}