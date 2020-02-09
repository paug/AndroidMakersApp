package fr.paug.androidmakers.model;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import fr.paug.androidmakers.R;

//TODO display presentation and video link if they exist
public class Session {

    public final String title;
    public final @Nullable String description;
    public final String language;
    public final int[] speakers;
    public final String subtype;
    public final String type;
    public final String experience;
    public final String presentation = "";
    public final @Nullable String videoURL;

    public Session(String title, String description, String language, int[] speakers, String subtype, String type, String experience, String videoURL) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.speakers = speakers;
        this.subtype = subtype;
        this.type = type;
        this.experience = experience;
        this.videoURL = videoURL;
    }

    @StringRes
    public int getLanguageName() {
        return Session.getLanguageFullName(this.language);
    }

    @StringRes
    public static int getLanguageFullName(String abbreviatedVersion) {
        if (!TextUtils.isEmpty(abbreviatedVersion)) {
            if ("en".equalsIgnoreCase(abbreviatedVersion)) {
                return R.string.english;
            } else if ("fr".equalsIgnoreCase(abbreviatedVersion)) {
                return R.string.french;
            }
        }
        return R.string.no_language;
    }

}
