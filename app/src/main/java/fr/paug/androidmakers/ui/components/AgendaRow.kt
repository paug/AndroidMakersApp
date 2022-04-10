package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.androidmakers.store.graphql.GetSessionQuery
import fr.androidmakers.store.graphql.GetSessionsQuery
import fr.androidmakers.store.graphql.fragment.SessionDetails
import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Session
import fr.androidmakers.store.model.Speaker
import fr.paug.androidmakers.ui.adapter.ScheduleSession
import fr.paug.androidmakers.ui.model.UiSession
import java.time.Instant

@Composable
fun AgendaRow(
    uiSession: UiSession
) {
  Box(
      modifier = Modifier.padding(4.dp)
  ) {
    Text(text = uiSession.title)
  }
}

@Preview
@Composable
fun AgendaRowPreview() {
  AgendaRow(fakeUiSession)
}

private val fakeUiSession = UiSession(
    title = "Why did the chicken cross the road?",
    language = "fr",
    speakers = listOf(UiSession.Speaker("chicken1")),
    room = "Moebius",
    complexity = "",
    startDate = Instant.parse("2022-04-25T09:00:00+02:00"),
    endDate = Instant.parse("2022-04-25T10:00:00+02:00"),
)