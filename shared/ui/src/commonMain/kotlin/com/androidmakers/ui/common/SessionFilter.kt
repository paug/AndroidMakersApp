package com.androidmakers.ui.common

data class SessionFilter(val type: FilterType, val value: Any) {
  enum class FilterType {
    BOOKMARK,
    LANGUAGE,
    ROOM
  }
}
