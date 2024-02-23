package fr.paug.androidmakers.ui.util

import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT

/**
 * Discards HTML information from a String
 *
 * In order to keep the style information, we'd need something that does HTML -> AnnotatedString
 * or Spanned -> AnnotatedString which doesn't seem to exist
 * See https://stackoverflow.com/questions/66494838/android-compose-how-to-use-html-tags-in-a-text-view#comment117551793_66494838
 */
internal fun String.discardHtmlTags(): String {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, FROM_HTML_MODE_COMPACT).toString()
  } else {
    Html.fromHtml(this).toString()
  }
}