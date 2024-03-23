@file:OptIn(ExperimentalHorologistApi::class)

package fr.paug.androidmakers.wear.ui.session.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Text
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumn
import com.google.android.horologist.compose.layout.rememberResponsiveColumnState
import com.google.android.horologist.compose.material.Chip
import com.google.android.horologist.compose.material.ListHeaderDefaults
import com.google.android.horologist.compose.material.ResponsiveListHeader
import fr.paug.androidmakers.wear.ui.main.SessionDetails

@Composable
fun SessionListScreen(sessions: List<SessionDetails>?) {
  if (sessions == null) {
    Loading()
  } else {
    SessionList(sessions)
  }
}

@Composable
private fun SessionList(sessions: List<SessionDetails>) {
  val columnState = rememberResponsiveColumnState()
  ScalingLazyColumn(
      columnState = columnState,
      modifier = Modifier.fillMaxSize()
  ) {
    item {
      ResponsiveListHeader(contentPadding = ListHeaderDefaults.firstItemPadding()) {
        Text(text = "Day 1") // TODO
      }
    }
    items(sessions, key = { it.session.id }) { session ->
      Chip(label = session.session.title, onClick = { })
    }
  }
}

@Composable
private fun Loading() {
  Box(
      modifier = Modifier
          .fillMaxSize(),
      contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun SessionListScreenPreview() {
  SessionListScreen(null)
}
