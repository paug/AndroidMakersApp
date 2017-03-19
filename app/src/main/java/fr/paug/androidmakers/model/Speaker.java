package fr.paug.androidmakers.model;

/**
 * Created by stan on 18/03/2017.
 */
public class Speaker {

    public final String name;
    public final String bio;
    public final String company;
    public final String surname;
    public final String thumbnailUrl;

    public Speaker(String name, String bio, String company, String surname, String thumbnailUrl) {
        this.name = name;
        this.bio = bio;
        this.company = company;
        this.surname = surname;
        this.thumbnailUrl = thumbnailUrl;
    }
}
