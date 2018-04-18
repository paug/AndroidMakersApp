package fr.paug.androidmakers.ui.util;

public class SessionFilter {
    public enum FilterType {
        BOOKMARK,
        LANGUAGE,
        ROOM
    }

    public static final String LANGUAGE_FRENCH = "fr";
    public static final String LANGUAGE_ENGLISH = "en";

    public final String value;
    public final FilterType type;

    public SessionFilter(FilterType filterType, String value) {
        this.type = filterType;
        this.value = value;
    }
}