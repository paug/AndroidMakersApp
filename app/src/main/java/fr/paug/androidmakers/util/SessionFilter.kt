package fr.paug.androidmakers.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SessionFilter(val type: FilterType, val value: String) : Parcelable {
    enum class FilterType {
        BOOKMARK,
        LANGUAGE,
        ROOM
    }
}