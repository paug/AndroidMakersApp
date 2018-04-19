package fr.paug.androidmakers.util;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

public final class YoutubeUtil {

    private YoutubeUtil() {
    }

    @Nullable
    public static String getVideoID(final String videoURL) {
        if (TextUtils.isEmpty(videoURL)) {
            return null;
        }

        final Uri parse = Uri.parse(videoURL);
        final String host = parse.getHost().toLowerCase();
        if (host.equalsIgnoreCase("youtu.be")) {
            return parse.getLastPathSegment();
        } else if (host.endsWith("youtube.com")) {
            return parse.getQueryParameter("v");
        }

        return null;
    }

}
