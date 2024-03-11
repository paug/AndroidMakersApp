package fr.androidmakers.domain.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

actual class UrlOpener(private val context: Context) {
  actual fun openUrl(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
  }
}
