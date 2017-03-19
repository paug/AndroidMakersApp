package fr.paug.androidmakers.model;

/**
 * Created by stan on 18/03/2017.
 */
public class Session {

    public final String title;
    public final String description;
    public final String language;
    public final int[] speakers;
    public final String subtype;

    public Session(String title, String description, String language,
                   int[] speakers, String subtype) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.speakers = speakers;
        this.subtype = subtype;
    }
}
