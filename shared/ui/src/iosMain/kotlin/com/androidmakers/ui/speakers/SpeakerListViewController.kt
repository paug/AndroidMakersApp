package com.androidmakers.ui.speakers

import androidx.compose.ui.window.ComposeUIViewController
import com.androidmakers.ui.theme.AndroidMakersTheme
import moe.tlaster.precompose.koin.koinViewModel
import platform.UIKit.UIViewController

fun SpeakerListViewController(onSpeakerClick: (String) -> Unit): UIViewController =
    ComposeUIViewController {

      AndroidMakersTheme {
        val viewModel = koinViewModel(vmClass = SpeakerListViewModel::class)
        SpeakerScreen(
            viewModel = viewModel,
            navigateToSpeakerDetails = onSpeakerClick
        )
      }
    }
