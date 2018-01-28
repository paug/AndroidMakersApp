package fr.paug.androidmakers.model;

/**
 * @author Adrien Vitti
 * @since 2018.01.14
 */
public class Venue {

//    public final String venueName;
//    public final String venueAddress;
//    public final String venueDirections;
//    public final String venueImageURL;
//
//    public Venue(String venueName, String venueAddress, String venueDirections, String venueImageURL) {
//        this.venueName = venueName;
//        this.venueAddress = venueAddress;
//        this.venueDirections = venueDirections;
//        this.venueImageURL = venueImageURL;
//    }

    public String address;
    public String coordinates;
    public String description;
    public String imageUrl;
    public String name;

    public Venue(String address, String coordinates, String description, String imageUrl, String name) {
        this.address = address;
        this.coordinates = coordinates;
        this.description = description;
        this.imageUrl = imageUrl;
        this.name = name;
    }

}