package com.androidmakers.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.theme.AndroidMakersTheme
import platform.UIKit.UIViewController


fun MainLayoutViewController(): UIViewController =
    ComposeUIViewController {
      AndroidMakersTheme {
        MainLayout(
            versionName = "1.0.0",
            versionCode = "1.0",
          signinCallbacks = SigninCallbacks(
            signin = {},
            signout = {}
          )
        )
      }
    }
