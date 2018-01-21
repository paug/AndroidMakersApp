package fr.paug.androidmakers.model;

/**
 * @author Adrien Vitti
 * @since 2018.01.14
 */

public class VenueInformations {

    public final String venueName;
    public final String venueAddress;
    public final String venueDirections;
    public final String venueImageURL;

    public VenueInformations(String venueName, String venueAddress, String venueDirections, String venueImageURL) {
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueDirections = venueDirections;
        this.venueImageURL = venueImageURL;
    }
}
