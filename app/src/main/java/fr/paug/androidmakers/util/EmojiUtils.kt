package fr.paug.androidmakers.util

object EmojiUtils {

    @JvmStatic
    fun getLanguageInEmoji(language: String?): String {
        return when (language?.toLowerCase()) {
            "english" -> "\uD83C\uDDEC\uD83C\uDDE7"
            "french" -> "\uD83C\uDDEB\uD83C\uDDF7"
            else -> "\uD83C\uDDEC\uD83C\uDDE7 \uD83C\uDDEB\uD83C\uDDF7"
        }
    }

}