package com.androidmakers.ui.speakers

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import at.asitplus.KmmResult
import com.androidmakers.ui.model.toLce
import com.androidmakers.ui.theme.AndroidMakersTheme
import fr.androidmakers.di.DepContainer
import platform.UIKit.UIViewController

fun SpeakerListViewController(): UIViewController =
    ComposeUIViewController {
      val depContainter = DepContainer()
      val speakersRepository = depContainter.speakersRepository
      val speakers by speakersRepository.getSpeakers().collectAsState(KmmResult.success(emptyList()))
      val content = speakers.toLce()

      AndroidMakersTheme {
        SpeakerScreen(
            content = content,
            navigateToSpeakerDetails = {}
        )
      }
    }
