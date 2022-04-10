package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.androidmakers.store.graphql.GetSessionQuery
import fr.androidmakers.store.graphql.GetSessionsQuery
import fr.androidmakers.store.graphql.fragment.SessionDetails
import fr.androidmakers.store.model.ScheduleSlot
import fr.androidmakers.store.model.Session
import fr.paug.androidmakers.ui.adapter.ScheduleSession

@Composable
fun AgendaRow(
    scheduleSession: GetSessionsQuery.Session
) {
  Box(
      modifier = Modifier.padding(4.dp)
  ) {

  }
}

@Preview
@Composable
fun AgendaRowPreview() {
  AgendaRow(scheduleSession)
}

private val scheduleSession = GetSessionsQuery.Session(
    __typename = "Session",
    sessionDetails = SessionDetails(
        id = "1",
        title = "Why did the chicken cross the road?",
        description = "Really, but why?",
        language = "fr",
        speakers = emptyList(),
        tags = emptyList(),
        room = SessionDetails.Room("1"),
        complexity = "",
        startDate = "2022-04-25T09:00:00+02:00",
        endDate = "2022-04-25T10:00:00+02:00",
    )
)