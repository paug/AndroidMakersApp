package com.androidmakers.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.about.AboutActions
import com.androidmakers.ui.theme.AndroidMakersTheme
import moe.tlaster.precompose.PreComposeApp
import platform.UIKit.UIViewController


fun MainLayoutViewController(): UIViewController =
    ComposeUIViewController {
      AndroidMakersTheme {
        MainLayout(
            agendaLayout = @androidx.compose.runtime.Composable {
              val agendaFilterDrawerState = rememberDrawerState(DrawerValue.Closed)
              Text("PLOP")
            },
            aboutActions = AboutActions(
                onFaqClick = {},
                onCodeOfConductClick =  {},
                onXHashtagClick =  {},
                onXLogoClick = {},
                onYouTubeLogoClick = {},
                onSponsorClick =  {},
            ),
            user = null,
            versionName = "1.0.0",
            versionCode = "1.0",
        )
      }

    }
