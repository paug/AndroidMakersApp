package com.androidmakers.ui

import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.theme.AndroidMakersTheme
import org.koin.compose.KoinContext
import platform.Foundation.NSBundle
import platform.UIKit.UIViewController


fun MainLayoutViewController(): UIViewController =
    ComposeUIViewController {
      val versionName = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString")
      val versionCode = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion")

      KoinContext {
        AndroidMakersTheme {
          MainLayout(
            versionName = versionName.toString(),
            versionCode = versionCode.toString(),
            signinCallbacks = SigninCallbacks(
              signin = {},
              signout = {}
            )
          )
        }
      }
    }
