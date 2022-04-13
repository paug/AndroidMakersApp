package fr.paug.androidmakers.ui.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.androidmakers.store.model.Agenda
import fr.androidmakers.store.model.Room
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.AgendaPager
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.fragment.agendaToDays
import fr.paug.androidmakers.ui.util.SessionFilter
import fr.paug.androidmakers.util.EmojiUtils

@Composable
fun AgendaLayout(
    agendaFilterDrawerState: DrawerState,
    onSessionClick: (sessionId: String, roomId: String, startTimestamp: Long, endTimestamp: Long) -> Unit,
) {
    var sessionFilters: List<SessionFilter> by remember { mutableStateOf(listOf()) }

    // XXX This is a hack to make the drawer appear from the right
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalDrawer(
            drawerState = agendaFilterDrawerState,
            drawerContent = {
                // XXX Go back to left to right for the contents
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    AgendaFilterDrawer(
                        rooms = listOf(
                            Room("", "Room 1"),
                            Room("", "Room 2"),
                            Room("", "Room 3"),
                            Room("", "Room 4")
                        ), // TODO get values from store
                        sessionFilters = sessionFilters,
                        onFiltersChanged = { newFilters -> sessionFilters = newFilters }
                    )
                }
            },
            content = {
                // XXX Go back to left to right for the contents
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    val agenda = AndroidMakersApplication.instance().store.getAgenda().collectAsState(initial = Unit)

                    val value = agenda.value
                    if (value is Agenda) {
                      val days = agendaToDays(value)

                      AgendaPager(
                          days = days,
                          filterList = sessionFilters,
                          onSessionClicked = {
                            onSessionClick(
                                it.id, it.roomId, it.startDate.toEpochMilli(), it.endDate.toEpochMilli()
                            )
                          }
                      )
                    } else {
                        LoadingLayout()
                    }
                }
            }
        )
    }
}

@Composable
private fun AgendaFilterDrawer(
    rooms: List<Room>,
    sessionFilters: List<SessionFilter>,
    onFiltersChanged: (List<SessionFilter>) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        HeaderItem(R.string.filter)
        FilterItem(
            text = stringResource(R.string.bookmarked),
            image = R.drawable.ic_bookmarked,
            checked = sessionFilters.any { it.type == SessionFilter.FilterType.BOOKMARK },
            onCheck = { checked ->
                val newSessionFilters = sessionFilters.toMutableList().apply {
                    removeAll { it.type == SessionFilter.FilterType.BOOKMARK }
                    if (checked) add(SessionFilter(SessionFilter.FilterType.BOOKMARK, ""))
                }
                onFiltersChanged(newSessionFilters)
            }
        )

        HeaderItem(R.string.language)
        val french = "French"
        FilterItem(
            text = stringResource(R.string.french),
            language = french,
            checked = sessionFilters.any { it.type == SessionFilter.FilterType.LANGUAGE && it.value == french },
            onCheck = { checked ->
                val newSessionFilters = sessionFilters.toMutableList().apply {
                    removeAll { it.type == SessionFilter.FilterType.LANGUAGE && it.value == french }
                    if (checked) add(SessionFilter(SessionFilter.FilterType.LANGUAGE, french))
                }
                onFiltersChanged(newSessionFilters)

            }
        )
        val english = "English"
        FilterItem(
            text = stringResource(R.string.english),
            language = english,
            checked = sessionFilters.any { it.type == SessionFilter.FilterType.LANGUAGE && it.value == english },
            onCheck = { checked ->
                val newSessionFilters = sessionFilters.toMutableList().apply {
                    removeAll { it.type == SessionFilter.FilterType.LANGUAGE && it.value == english }
                    if (checked) add(SessionFilter(SessionFilter.FilterType.LANGUAGE, english))
                }
                onFiltersChanged(newSessionFilters)
            }
        )

        HeaderItem(R.string.rooms)
        for (room in rooms) {
            FilterItem(
                text = room.name,
                checked = sessionFilters.any { it.type == SessionFilter.FilterType.ROOM && it.value == room.name },
                onCheck = { checked ->
                    val newSessionFilters = sessionFilters.toMutableList().apply {
                        removeAll { it.type == SessionFilter.FilterType.ROOM && it.value == room.name }
                        if (checked) add(SessionFilter(SessionFilter.FilterType.ROOM, room.name))
                    }
                    onFiltersChanged(newSessionFilters)
                }
            )
        }
    }
}

@Composable
private fun FilterItem(
    text: String,
    image: Int? = null,
    language: String? = null,
    checked: Boolean,
    onCheck: (checked: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
              onCheck(!checked)
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val textLeftMargin = 48.dp
        if (image != null) {
            Image(
                modifier = Modifier.width(textLeftMargin),
                painter = painterResource(image), contentDescription = text
            )
        }
        if (language != null) {
            Text(
                modifier = Modifier.width(textLeftMargin),
                textAlign = TextAlign.Center,
                text = EmojiUtils.getLanguageInEmoji(language)!!
            )
        }
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = if (image == null && language == null) textLeftMargin else 0.dp),
            text = text,
            fontSize = 14.sp
        )
        Checkbox(modifier = Modifier
            .width(48.dp)
            .height(48.dp), checked = checked, onCheckedChange = null)
    }
}

@Composable
private fun HeaderItem(text: Int) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.light_grey))
            .padding(12.dp),
        fontSize = 16.sp,
        text = stringResource(text)
    )
}


@Preview
@Composable
private fun AgendaFilterDrawerPreview() {
    AgendaFilterDrawer(
        rooms = listOf(
            Room("", "Room 1"),
            Room("", "Room 2"),
            Room("", "Room 3"),
            Room("", "Room 4")
        ),
        sessionFilters = listOf(
            SessionFilter(type = SessionFilter.FilterType.BOOKMARK, value = "")
        ),
        onFiltersChanged = {}
    )
}


@Preview
@Composable
private fun AgendaLayoutPreview() {
    AgendaLayout(
        agendaFilterDrawerState = DrawerState(DrawerValue.Closed, confirmStateChange = { true }),
        onSessionClick = { _, _, _, _ -> }
    )
}
