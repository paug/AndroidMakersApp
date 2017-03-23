package fr.paug.androidmakers.model;

/**
 * @author Adrien Vitti
 * @since 2017.03.23
 */

public class Partners {

    private final String name;
    private final String imageUrl;
    private final String link;
    private final String description;

    public Partners(String name, String imageUrl, String link, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.link = link;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Partners{" +
                "name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}