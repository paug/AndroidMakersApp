package fr.paug.androidmakers.ui.components.agenda

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.AgendaRow
import fr.paug.androidmakers.ui.model.UISession

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgendaColumn(
    sessionsPerStartTime: Map<String, List<UISession>>,
    onSessionClicked: (UISession) -> Unit,
) {
  val listState = rememberLazyListState()
  var itemNum by remember { mutableStateOf(0) }
  LazyColumn(
      state = listState,
      modifier = Modifier.fillMaxHeight()
  ) {
    sessionsPerStartTime.forEach {

      stickyHeader {
        TimeSeparator(it.key)
      }

      itemsIndexed(
          items = it.value
      ) { index, uiSession ->

        AgendaRow(
            modifier = Modifier
                .clickable { onSessionClicked.invoke(uiSession) }
                .animateItemPlacement(),
            uiSession = uiSession,
        )

      }
    }
  }
}

@Composable
fun TimeSeparator(prettyTime: String) {
  Surface {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
          modifier = Modifier.size(18.dp),
          imageVector = Icons.Rounded.Schedule,
          tint = MaterialTheme.colorScheme.primary,
          contentDescription = stringResource(R.string.filter),
      )
      Text(
          text = prettyTime,
          style = MaterialTheme.typography.titleMedium.copy(
              color = MaterialTheme.colorScheme.primary,
              fontWeight = FontWeight.Bold,
              fontSize = 18.sp
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
