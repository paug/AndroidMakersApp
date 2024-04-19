package com.androidmakers.ui.agenda

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.model.UISession
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgendaColumn(
  sessionsPerStartTime: Map<String, List<UISession>>,
  onSessionClicked: (UISession) -> Unit,
  onSessionBookmarked: (UISession, Boolean) -> Unit,
  onApplyForAppClinicClicked: () -> Unit,
) {
  val listState = rememberLazyListState()

  LazyColumn(
    state = listState,
    modifier = Modifier.fillMaxHeight(),
    contentPadding = PaddingValues(8.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp),
    horizontalAlignment = Alignment.CenterHorizontally

  ) {
    sessionsPerStartTime.forEach { (key, sessions) ->

      stickyHeader {
        TimeSeparator(key)
      }

      item {
        Card(
          modifier = Modifier.padding(horizontal = 16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
          ),
          shape = RoundedCornerShape(16.dp),
        ) {
          sessions.forEach { uiSession ->
            if (uiSession.isServiceSession) {
              ServiceSessionRow(
                uiSession,
                modifier = Modifier.animateItemPlacement()
              )
            } else {
              SessionRow(
                modifier = Modifier.animateItemPlacement(),
                uiSession = uiSession,
                onSessionClicked = onSessionClicked,
                onSessionBookmarked = onSessionBookmarked,
                onApplyForAppClinic = onApplyForAppClinicClicked
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun TimeSeparator(prettyTime: String) {
  Surface(
    color = MaterialTheme.colorScheme.background
  ) {
    Row(
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxWidth()
        .clearAndSetSemantics {
                              contentDescription = timeContentDescription(prettyTime)
        },
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Icon(
        modifier = Modifier.size(24.dp),
        imageVector = Icons.Rounded.Schedule,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = null,
      )
      Text(
        text = prettyTime,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleSmall
      )
    }
  }
}

@Preview
@Composable
private fun TimeSeparatorPreview() {
  TimeSeparator(prettyTime = "3:25 pm")
}


private fun timeContentDescription(string: String): String {
  return string.replace("([0-9]{1,2}):([0-9]{1,2})", "$1 heures $2 secondes");
}
