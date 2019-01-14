package fr.paug.androidmakers.util;

import android.net.Uri;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

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
            final String id = parse.getQueryParameter("v");
            if (TextUtils.isEmpty(id)) {
                final List<String> pathSegments = parse.getPathSegments();
                if (pathSegments != null && pathSegments.size() == 2
                        && pathSegments.get(0).equalsIgnoreCase("embed")) {
                    return pathSegments.get(1);
                }
            }
            return id;
        }

        return null;
    }

    @Nullable
    public static Uri getVideoUri(final String videoURL) {
        final String videoID = getVideoID(videoURL);

        if (TextUtils.isEmpty(videoID)) {
            return null;
        }

        return Uri.parse("https://youtu.be/" + videoID);
    }

}
