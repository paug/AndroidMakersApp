package fr.paug.androidmakers.ui.activity

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import fr.androidmakers.store.model.Room
import fr.androidmakers.store.model.Session
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.LoadingLayout
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import java.util.Date
import java.util.Formatter

sealed class SessionDetailState {
    object Loading : SessionDetailState()
    data class Loaded(
        val session: Session,
        val room: Room,
        val startTimestamp: Long,
        val endTimestamp: Long,
    ) : SessionDetailState()
}

@Composable
fun SessionDetailLayout(
    sessionDetailState: SessionDetailState,
    onBackClick: () -> Unit,
) {
    AndroidMakersTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                        }
                    },
                    title = {
                        if (sessionDetailState is SessionDetailState.Loaded) {
                            Text(sessionDetailState.session.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    },
                    actions = {
                        if (sessionDetailState is SessionDetailState.Loaded) {
                            IconButton(
                                onClick = {
                                    // TODO
                                }
                            ) {
                                Icon(Icons.Default.Share, contentDescription = stringResource(R.string.share))
                            }
                        }
                    }
                )
            },
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                when (sessionDetailState) {
                    is SessionDetailState.Loading -> LoadingLayout()
                    is SessionDetailState.Loaded -> SessionDetails(sessionDetailState)
                }
            }
        }
    }
}

@Composable
private fun SessionDetails(sessionDetails: SessionDetailState.Loaded) {
    Column(Modifier
        .padding(16.dp)
        .verticalScroll(state = rememberScrollState())
    ) {
        Text(text = sessionDetails.session.title, style = MaterialTheme.typography.h5)
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = getFormattedDateAndRoom(
                room = sessionDetails.room,
                startTimestamp = sessionDetails.startTimestamp,
                endTimestamp = sessionDetails.endTimestamp,
            ),
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = sessionDetails.session.description,
            style = MaterialTheme.typography.body1
        )
        FlowRow(
            modifier = Modifier.padding(top = 16.dp),
            mainAxisSpacing = 8.dp,
        ) {
            sessionDetails.session.language.asLanguageResource()?.let { language ->
                Chip(language)
            }
            for (tag in sessionDetails.session.tags) {
                Chip(tag)
            }
            if (sessionDetails.session.complexity.isNotEmpty()) {
                Chip(sessionDetails.session.complexity)
            }
        }
    }
}

@Composable
private fun String.asLanguageResource(): String? {
    return when (this) {
        "English" -> stringResource(R.string.english)
        "French" -> stringResource(R.string.french)
        else -> null
    }
}


@Composable
private fun Chip(text: String) {
    Text(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                color = colorResource(R.color.light_grey)
            )
            .padding(8.dp),
        text = text
    )
}

@Composable
private fun getFormattedDateAndRoom(room: Room, startTimestamp: Long, endTimestamp: Long): String {
    val context = LocalContext.current
    val sessionDate = DateUtils.formatDateRange(context,
        Formatter(context.resources.configuration.locale),
        startTimestamp,
        endTimestamp,
        DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR,
        null).toString()
    return if (room.name.isNotEmpty()) {
        stringResource(R.string.sessionDateWithRoomPlaceholder, sessionDate, room.name)
    } else {
        sessionDate
    }
}

@Preview
@Composable
private fun SessionDetailLayoutLoadingPreview() {
    SessionDetailLayout(
        sessionDetailState = SessionDetailState.Loading,
        onBackClick = {}
    )
}

@Preview
@Composable
private fun SessionDetailLayoutLoadedPreview() {
    SessionDetailLayout(
        sessionDetailState = SessionDetailState.Loaded(
            session = Session(
                id = "42",
                complexity = "Intermediate",
                speakers = listOf("John Smith", "Jane Doe"),
                description = "Ever wish you could just use a lambda as an onClickListener? Or change the View visibility by modifying a simple \"isVisible\" boolean? Or how about using a doOnLayout{} method to run a block of code after view layout?\r\n\r\nThese are just a few examples of how Android KTX extensions help solve common problems. They improve the existing Jetpack and Android platform APIs so you can consume them from Kotlin in a concise and idiomatic way.\r\n\r\nThis talk will discuss what KTX extensions are and explore the most useful extensions that you can use in your daily workflow.",
                language = "English",
                title = "The title of this session",
                tags = listOf("Tools", "Fun"),
                videoURL = "",
                slido = null,
                platformUrl = null
            ),
            room = Room(
                id = "13",
                name = "The Big Room"
            ),
            startTimestamp = Date(122, 4, 26, 13, 30).time,
            endTimestamp = Date(122, 4, 26, 14, 15).time
        ),
        onBackClick = {}
    )
}
