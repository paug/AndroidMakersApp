package fr.paug.androidmakers.ui.util;

public class SessionFilter {

    public enum FilterType {
        BOOKMARK,
        LANGUAGE,
        ROOM
    }

    public final Object value;
    public final FilterType type;

    public SessionFilter(FilterType filterType, Object value) {
        this.type = filterType;
        this.value = value;
    }
}