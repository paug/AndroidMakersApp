package com.androidmakers.ui

import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.platform.AccessibilitySyncOptions
import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.theme.AndroidMakersTheme
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.UIKit.UIViewController


@OptIn(ExperimentalComposeApi::class)
fun MainLayoutViewController(): UIViewController =
    ComposeUIViewController(
      configure = {
        accessibilitySyncOptions = AccessibilitySyncOptions.Always(debugLogger = null)
      }
    ) {
      val versionName = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString")
      val versionCode = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion")

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
