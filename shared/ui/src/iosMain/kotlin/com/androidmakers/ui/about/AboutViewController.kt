package com.androidmakers.ui.about

import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.theme.AndroidMakersTheme
import fr.androidmakers.di.DepContainer
import platform.Foundation.NSBundle
import platform.UIKit.UIViewController

fun AboutViewController(): UIViewController =
    ComposeUIViewController {
      val depContainer = DepContainer()

      AndroidMakersTheme {
        val versionName = NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String
        val versionCode = NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String
        AboutScreen(
            versionCode = versionCode ?: "",
            versionName = versionName ?: "",
            aboutActions = AboutActions(
                onFaqClick = { depContainer.openFaqUseCase() },
                onSponsorClick = { depContainer.openPartnerLinkUseCase(it) },
                onYouTubeLogoClick = { depContainer.openYoutubeUseCase() },
                onXHashtagClick = { depContainer.openXHashtagUseCase() },
                onXLogoClick = { depContainer.openXAccountUseCase() },
                onCodeOfConductClick = { depContainer.openCocUseCase() }
            )
        )
      }
    }
