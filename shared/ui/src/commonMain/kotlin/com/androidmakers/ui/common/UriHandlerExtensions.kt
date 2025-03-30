package com.androidmakers.ui.common

import androidx.compose.ui.platform.UriHandler
import fr.androidmakers.domain.utils.UrlOpener

private class UriHandlerUrlOpener(private val uriHandler: UriHandler) : UrlOpener {
  override fun openUrl(url: String): Boolean {
    return try {
      uriHandler.openUri(url)
      true
    } catch (e: IllegalArgumentException) {
      e.printStackTrace()
      false
    }
  }
}

fun UriHandler.toUrlOpener(): UrlOpener = UriHandlerUrlOpener(this)
