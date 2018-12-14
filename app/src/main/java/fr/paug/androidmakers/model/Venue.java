package fr.paug.androidmakers.model;

public class Venue {

    public String address = "";
    public String coordinates = "";
    public String description = "";
    public String descriptionFr = "";
    public String imageUrl = "";
    public String name = "No Venue";

    public Venue() {
    }

    public Venue(String address, String coordinates, String description, String descriptionFr, String imageUrl, String name) {
        this.address = address;
        this.coordinates = coordinates;
        this.description = description;
        this.descriptionFr = descriptionFr;
        this.imageUrl = imageUrl;
        this.name = name;
    }

}