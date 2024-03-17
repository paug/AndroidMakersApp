package com.androidmakers.ui.sponsors

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import at.asitplus.KmmResult
import com.androidmakers.ui.model.Lce
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
