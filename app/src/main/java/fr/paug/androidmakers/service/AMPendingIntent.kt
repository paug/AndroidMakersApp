package fr.paug.androidmakers.service

import android.app.PendingIntent
import android.os.Build

object AMPendingIntentFlags {
  val IMMUTABLE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    PendingIntent.FLAG_IMMUTABLE
  } else {
    0
  }
}
