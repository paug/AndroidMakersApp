package fr.androidmakers.domain

import android.content.Context

actual class PlatformContext(val context: Context) {
  actual val androidContextOrNull: Any?
    get() = context
}
