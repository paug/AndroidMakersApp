package com.androidmakers.ui.sponsors

import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.theme.AndroidMakersTheme
import fr.androidmakers.di.DepContainer
import platform.UIKit.UIViewController


fun SponsorViewController(): UIViewController =
    ComposeUIViewController {
      val depContainter = DepContainer()
      AndroidMakersTheme {
        SponsorsScreen { partner ->
          depContainter.openPartnerLinkUseCase(partner)
        }
      }
    }
