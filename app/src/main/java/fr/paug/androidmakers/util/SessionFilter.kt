package fr.paug.androidmakers.util

data class SessionFilter(val type: FilterType, val value: Any) {
  enum class FilterType {
    BOOKMARK,
    LANGUAGE,
    ROOM
  }
}