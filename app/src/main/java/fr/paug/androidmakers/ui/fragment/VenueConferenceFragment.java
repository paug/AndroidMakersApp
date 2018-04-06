package fr.paug.androidmakers.ui.fragment;

import android.net.Uri;

import fr.paug.androidmakers.manager.AgendaRepository;
import fr.paug.androidmakers.model.Venue;

public class VenueConferenceFragment extends AbstractVenueFragment {

    private final String CONFERENCE_VENUE_COORDINATES_URI =
            "geo:" + getVenueInformation().coordinates +
                    "?q=" + Uri.encode("" + getVenueInformation().name);

    protected Uri getVenueCoordinatesUri() {
        return Uri.parse(CONFERENCE_VENUE_COORDINATES_URI);
    }

    @Override
    protected Venue getVenueInformation() {
        Venue conference = AgendaRepository.getInstance().getVenue(1);
        if (conference != null) {
            return conference;
        } else {
            return new Venue();
        }
    }

}