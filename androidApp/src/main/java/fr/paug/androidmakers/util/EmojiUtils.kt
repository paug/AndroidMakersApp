package fr.paug.androidmakers.util

import java.util.Locale

object EmojiUtils {

  fun getLanguageInEmoji(language: String?): String? {
    return when (language?.lowercase(Locale.ROOT)) {
      "english" -> "\uD83C\uDDEC\uD83C\uDDE7"
      "french" -> "\uD83C\uDDEB\uD83C\uDDF7"
      else -> null
    }
  }
}