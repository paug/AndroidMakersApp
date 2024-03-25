@file:Suppress("CAST_NEVER_SUCCEEDS")

package fr.androidmakers.domain.utils

import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
actual fun String.removeHtmlTags(): String {
  /*if (this.isBlank()) {
    return ""
  } else {
    return NSString.create(string = this).dataUsingEncoding(NSUTF8StringEncoding)?.let { data ->
      val attributed = NSAttributedString.create(
          data,
          mapOf(NSDocumentTypeDocumentOption to NSHTMLTextDocumentType),
          null,
          null
      )
      return attributed?.string ?: ""
    } ?: ""
  }*/
  // TODO the above method does not work
  return this
}
