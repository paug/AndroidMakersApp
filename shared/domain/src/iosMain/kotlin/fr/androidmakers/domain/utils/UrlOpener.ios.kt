package fr.androidmakers.domain.utils

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class UrlOpener {
  actual fun openUrl(url: String) {
    val urlObj = NSURL(string = url)
    if (UIApplication.sharedApplication.canOpenURL(urlObj)) {
      UIApplication.sharedApplication.openURL(urlObj)
    }
  }
}
