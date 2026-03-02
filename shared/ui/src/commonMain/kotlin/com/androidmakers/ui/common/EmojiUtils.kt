package com.androidmakers.ui.common

object EmojiUtils {

  fun getLanguageInEmoji(language: String?): String? {
    if (language == null) return null
    val lower = language.lowercase()
    return when {
      lower.startsWith("fr") -> "\uD83C\uDDEB\uD83C\uDDF7"
      lower.startsWith("en") -> "\uD83C\uDDEC\uD83C\uDDE7"
      else -> null
    }
  }
}
