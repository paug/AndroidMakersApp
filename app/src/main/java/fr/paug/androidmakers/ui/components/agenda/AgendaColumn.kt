package fr.paug.androidmakers.ui.components.agenda

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.model.UISession

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgendaColumn(
    sessionsPerStartTime: Map<String, List<UISession>>,
    onSessionClicked: (UISession) -> Unit,
) {
  val listState = rememberLazyListState()

  LazyColumn(
      state = listState,
      modifier = Modifier.fillMaxHeight(),
      contentPadding = PaddingValues(8.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
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
            AgendaRow(
                modifier = Modifier.animateItemPlacement(),
                uiSession = uiSession,
                onSessionClicked =onSessionClicked
            )
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
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
          modifier = Modifier.size(24.dp),
          imageVector = Icons.Rounded.Schedule,
          tint = MaterialTheme.colorScheme.primary,
          contentDescription = stringResource(R.string.filter),
      )
      Text(
          text = prettyTime,
          style = MaterialTheme.typography.headlineSmall.copy(
              color = MaterialTheme.colorScheme.primary
          )
      )
    }
  }
}

@Preview
@Composable
private fun TimeSeparatorPreview() {
  TimeSeparator(prettyTime = "3:25 pm")
}
