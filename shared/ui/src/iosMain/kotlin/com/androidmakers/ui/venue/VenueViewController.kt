package com.androidmakers.ui.venue

import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.theme.AndroidMakersTheme
import platform.UIKit.UIViewController

fun VenueViewController(): UIViewController =
    ComposeUIViewController {

      AndroidMakersTheme {
        VenuePager()
      }
    }
