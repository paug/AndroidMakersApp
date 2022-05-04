package fr.paug.androidmakers.util

class SessionFilter(val type: FilterType, val value: Any) {
    enum class FilterType {
        BOOKMARK,
        LANGUAGE,
        ROOM
    }
}