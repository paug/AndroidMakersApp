package com.androidmakers.ui.common

object EmojiUtils {

  fun getLanguageInEmoji(language: String?): String? {
    return when (language?.lowercase()) {
      "english" -> "\uD83C\uDDEC\uD83C\uDDE7"
      "french" -> "\uD83C\uDDEB\uD83C\uDDF7"
      else -> null
    }
  }
}
