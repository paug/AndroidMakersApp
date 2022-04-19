package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.ui.model.UISession
import fr.paug.androidmakers.ui.theme.AMAlpha
import separatorColor
import separatorHeight
import surfaceColor2

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

  // Scroll to current time
//  LaunchedEffect(sessionsPerStartTime) {
//    listState.scrollToItem(index = findStartIndex(sessionsPerStartTime))
//  }

  LazyColumn(
      state = listState,
      modifier = Modifier.fillMaxHeight()
  ) {
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
          .background(surfaceColor2())
  ) {
    Text(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
        text = prettyTime,
        style = TextStyle(
            fontFeatureSettings = "smcp"
        )
    )
    Surface(
        modifier = Modifier
            .height(separatorHeight)
            .fillMaxWidth(),
        color = separatorColor()
    ) {}
  }
}

@Preview
@Composable
private fun TimeSeparatorPreview() {
    TimeSeparator(prettyTime = "3:25 pm")
}
