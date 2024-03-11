package fr.androidmakers.domain.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class UrlOpener {
  actual fun openUrl(url: String): Boolean {
    val urlObj = NSURL(string = url)
    return if (UIApplication.sharedApplication.canOpenURL(urlObj)) {
      UIApplication.sharedApplication.openURL(urlObj)
      true
    } else { false }
  }
}
