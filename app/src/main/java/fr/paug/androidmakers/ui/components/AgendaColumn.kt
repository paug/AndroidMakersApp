package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.ui.model.UISession

private fun findStartIndex(sessionsPerStartTime: Map<String, List<UISession>>): Int {
  val now = System.currentTimeMillis()
  var found = false
  val items = sessionsPerStartTime.flatMap {
    listOf(it.key) + listOf(it.value)
  }

  for (i in items.indices.reversed()) {
    val item = items[i]
    if (item is UISession && item.startDate.toEpochMilli() < now) {
      found = true
    } else if( item is String) {
      if (found) {
        return i
      }
    }
  }

  return 0
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AgendaColumn(
    sessionsPerStartTime: Map<String, List<UISession>>,
    onSessionClicked: (UISession) -> Unit,
) {
  val listState = rememberLazyListState()

  LaunchedEffect(sessionsPerStartTime) {
    // Animate scroll to the 10th item
    listState.scrollToItem(index = findStartIndex(sessionsPerStartTime))
  }

  LazyColumn(state = listState) {
    sessionsPerStartTime.forEach {
      stickyHeader {
        TimeSeparator(it.key)
      }

      items(it.value) { uiSession ->
        AgendaRow(
            uiSession = uiSession,
            modifier = Modifier.clickable {
              onSessionClicked.invoke(uiSession)
            }
        )
      }
    }
  }
}

@Composable
fun TimeSeparator(prettyTime: String) {
  Column(
      modifier = Modifier
          .fillMaxWidth()
          .background(Color.LightGray)
  ) {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        text = prettyTime
    )
    Surface(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth(),
        color = Color.DarkGray
    ) {}
  }
}

@Preview
@Composable
fun TimeSeparatorPreview() {
  TimeSeparator(prettyTime = "3:25 pm")
}