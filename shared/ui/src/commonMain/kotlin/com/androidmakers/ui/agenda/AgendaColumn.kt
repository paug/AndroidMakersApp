package com.androidmakers.ui.agenda

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.model.UISession
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

      items(count = sessions.size, key = { sessions[it].id }) { index ->
        val uiSession = sessions[index]
        if (uiSession.isServiceSession) {
          ServiceSessionRow(
            uiSession,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp).animateItem()
          )
        } else {
          SessionRow(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp).animateItem(),
            uiSession = uiSession,
            onSessionClick = onSessionClick,
            onSessionBookmark = onSessionBookmark,
            onApplyForAppClinicClick = onApplyForAppClinicClick,
          )
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
        .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      Text(
        text = prettyTime,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )
      HorizontalDivider(
        modifier = Modifier.weight(1f),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.outlineVariant,
      )
    }
  }
}

@Preview
@Composable
private fun TimeSeparatorPreview() {
  TimeSeparator(prettyTime = "3:25 pm")
}
