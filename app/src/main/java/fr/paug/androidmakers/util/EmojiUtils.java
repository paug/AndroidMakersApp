package fr.paug.androidmakers.util;

public final class EmojiUtils {

    public static String getLanguageInEmoji(String language) {
        if ("en".equalsIgnoreCase(language)) {
            return "\uD83C\uDDEC\uD83C\uDDE7";
        } else if ("fr".equalsIgnoreCase(language)) {
            return "\uD83C\uDDEB\uD83C\uDDF7";
        } else {
            return "\uD83C\uDDEC\uD83C\uDDE7 \uD83C\uDDEB\uD83C\uDDF7";
        }
    }

}
