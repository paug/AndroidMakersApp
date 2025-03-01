package com.androidmakers.ui.common

object EmojiUtils {

  fun getLanguageInEmoji(language: String?): String? {
    return when {
      language.equals("english", ignoreCase = true) -> "\uD83C\uDDEC\uD83C\uDDE7"
      language.equals("french", ignoreCase = true) -> "\uD83C\uDDEB\uD83C\uDDF7"
      else -> null
    }
  }
}
