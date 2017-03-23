package fr.paug.androidmakers.model;

/**
 * The class description here.
 *
 * @author Adrien Vitti
 * @since 2017.03.23
 */

public class Partners {
    public final String name;
    public final String imageUrl;
    public final String link;
    public final String description;

    public Partners(String name, String imageUrl, String link, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
    }
}
