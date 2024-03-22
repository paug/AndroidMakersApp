package fr.androidmakers.domain.utils

import android.text.Html

actual fun String.removeHtmlTags(): String {
  /**
   * Discards HTML information from a String
   *
   * In order to keep the style information, we'd need something that does HTML -> AnnotatedString
   * or Spanned -> AnnotatedString which doesn't seem to exist
   * See https://stackoverflow.com/questions/66494838/android-compose-how-to-use-html-tags-in-a-text-view#comment117551793_66494838
   */
    return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT).toString()
}
