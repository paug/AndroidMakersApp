package fr.paug.androidmakers.model;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;

import fr.paug.androidmakers.R;

public class Session {

    public final String title;
    public final @Nullable String description;
    public final String language;
    public final int[] speakers;
    public final String subtype;
    public final String type;
    public final String experience;
    public final String presentation = "";
    public final String video = "";

    public Session(String title, String description, String language, int[] speakers, String subtype, String type, String experience) {
        this.title = title;
        this.description = description;
        this.language = language;
        this.speakers = speakers;
        this.subtype = subtype;
        this.type = type;
        this.experience = experience;
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
        return 0;
    }

}
