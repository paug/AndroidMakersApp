package fr.paug.androidmakers.model

import android.text.TextUtils
import androidx.annotation.StringRes
import fr.paug.androidmakers.R
import fr.paug.androidmakers.model.SocialNetworkHandle
import fr.paug.androidmakers.model.Ribbon
import androidx.annotation.DrawableRes
import fr.paug.androidmakers.model.SocialNetworkHandle.SocialNetworkType

//TODO display presentation and video link if they exist
class Session(
    val title: String,
    val description: String?,
    val language: String,
    val speakers: IntArray,
    val subtype: String,
    val type: String,
    val experience: String,
    val videoURL: String?
) {
    val presentation = ""

    @get:StringRes
    val languageName: Int
        get() = getLanguageFullName(language)

    companion object {
        @StringRes
        fun getLanguageFullName(abbreviatedVersion: String?): Int {
            if (!TextUtils.isEmpty(abbreviatedVersion)) {
                if ("en".equals(abbreviatedVersion, ignoreCase = true)) {
                    return R.string.english
                } else if ("fr".equals(abbreviatedVersion, ignoreCase = true)) {
                    return R.string.french
                }
            }
            return R.string.no_language
        }
    }
}