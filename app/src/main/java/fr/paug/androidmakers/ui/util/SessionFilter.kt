package fr.paug.androidmakers.ui.util

class SessionFilter(val type: FilterType, val value: Any) {

    enum class FilterType {
        BOOKMARK,
        LANGUAGE,
        ROOM
    }

}