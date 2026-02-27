package com.androidmakers.ui.common

import androidx.compose.runtime.Composable
import io.openfeedback.OpenFeedback

@Composable
actual fun OpenFeedbackLayout(projectId: String, sessionId: String) {
  OpenFeedback(
    projectId = projectId,
    sessionId = sessionId,
  )
}
