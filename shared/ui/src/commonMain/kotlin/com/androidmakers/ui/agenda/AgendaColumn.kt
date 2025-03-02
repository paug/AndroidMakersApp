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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.filter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgendaColumn(
  sessionsPerStartTime: Map<String, List<UISession>>,
  onSessionClick: (UISession) -> Unit,
  onSessionBookmark: (UISession, Boolean) -> Unit,
  onApplyForAppClinicClick: () -> Unit,
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
          sessions.forEachIndexed { index, uiSession ->
            val sessionBeforeIsServiceSession = remember {
              derivedStateOf {
                if (index > 0) {
                  sessions[index - 1].isServiceSession
                } else {
                  false
                }
              }
            }

            val sessionAfterIsServiceSession = remember {
              derivedStateOf {
                if (index < sessions.lastIndex) {
                  sessions[sessions.lastIndex].isServiceSession
                } else {
                  false
                }
              }
            }

            if (uiSession.isServiceSession) {
              ServiceSessionRow(
                uiSession,
                modifier = Modifier.animateItem()
              )
            } else {
              SessionRow(
                modifier = Modifier.animateItem(),
                uiSession = uiSession,
                onSessionClick = onSessionClick,
                onSessionBookmark = onSessionBookmark,
                onApplyForAppClinicClick = onApplyForAppClinicClick,
                sessionBeforeIsServiceSession = sessionBeforeIsServiceSession.value,
                sessionAfterIsServiceSession = sessionAfterIsServiceSession.value
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
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Icon(
        modifier = Modifier.size(24.dp),
        imageVector = Icons.Rounded.Schedule,
        tint = MaterialTheme.colorScheme.primary,
        contentDescription = stringResource(Res.string.filter),
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
