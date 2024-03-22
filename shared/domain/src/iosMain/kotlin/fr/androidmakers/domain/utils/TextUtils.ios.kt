@file:Suppress("CAST_NEVER_SUCCEEDS")

package fr.androidmakers.domain.utils

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSAttributedString
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.UIKit.NSDocumentTypeDocumentAttribute
import platform.UIKit.NSDocumentTypeDocumentOption
import platform.UIKit.NSHTMLTextDocumentType
import platform.UIKit.create

@OptIn(ExperimentalForeignApi::class)
actual fun String.removeHtmlTags(): String {

  if (this.isBlank()) {
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
  }
}
