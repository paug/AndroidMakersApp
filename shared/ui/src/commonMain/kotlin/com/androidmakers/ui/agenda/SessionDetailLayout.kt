package com.androidmakers.ui.agenda

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.common.OpenFeedbackLayout
import com.androidmakers.ui.common.separatorColor
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.getPlatformContext
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.SessionDetailState
import com.androidmakers.ui.theme.AMColor
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.model.isAppClinic
import fr.androidmakers.domain.utils.formatTimeInterval
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.about_this_talk
import fr.paug.androidmakers.ui.back
import fr.paug.androidmakers.ui.bookmarked
import fr.paug.androidmakers.ui.english
import fr.paug.androidmakers.ui.feedbackWaiting
import fr.paug.androidmakers.ui.french
import fr.paug.androidmakers.ui.notification
import fr.paug.androidmakers.ui.session_app_clinic_apply
import fr.paug.androidmakers.ui.share
import fr.paug.androidmakers.ui.speaker_section
import fr.paug.androidmakers.ui.speakers
import fr.paug.androidmakers.ui.talk_details
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.Duration

@Composable
fun SessionDetailScreen(
  viewModel: SessionDetailViewModel,
  onBackClick: () -> Unit,
  onSpeakerClick: (speakerId: String) -> Unit,
  sharedTransitionScope: SharedTransitionScope,
  animatedVisibilityScope: AnimatedVisibilityScope,
) {
  val sessionDetailState by viewModel.sessionDetailState.collectAsStateWithLifecycle()
  val context = getPlatformContext()
  val urlOpener = LocalUriHandler.current.toUrlOpener()

  SessionDetailLayout(
    sessionDetailState = sessionDetailState,
    onBackClick = onBackClick,
    onBookmarkClick = {
      viewModel.bookmark(it)
    },
    onShareSession = {
      viewModel.shareSession(context)
    },
    onOpenLink = {
      viewModel.openLink(urlOpener, it)
    },
    onApplyForAppClinic = {
      viewModel.applyForAppClinic(urlOpener)
    },
    onSpeakerClick = onSpeakerClick,
    sharedTransitionScope = sharedTransitionScope,
    animatedVisibilityScope = animatedVisibilityScope,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailLayout(
  sessionDetailState: Lce<SessionDetailState>,
  onBackClick: () -> Unit,
  onBookmarkClick: (bookmarked: Boolean) -> Unit,
  onShareSession: () -> Unit,
  onOpenLink: (SocialsItem) -> Unit,
  onApplyForAppClinic: () -> Unit,
  onSpeakerClick: (speakerId: String) -> Unit,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      TopAppBar(
        navigationIcon = {
          IconButton(onClick = onBackClick) {
            Icon(
              Icons.AutoMirrored.Rounded.ArrowBack,
              contentDescription = stringResource(Res.string.back)
            )
          }
        },
        title = {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              painter = painterResource(Res.drawable.notification),
              contentDescription = null,
              modifier = Modifier.size(24.dp)
            )
            Text(
              text = stringResource(Res.string.talk_details),
              style = MaterialTheme.typography.titleMedium
            )
          }
        },
        actions = {
          if (sessionDetailState is Lce.Content) {
            IconButton(onClick = onShareSession) {
              Icon(
                Icons.Rounded.Share,
                contentDescription = stringResource(Res.string.share)
              )
            }
          }
        },
        scrollBehavior = scrollBehavior
      )
    },
    floatingActionButton = {
      if (sessionDetailState is Lce.Content) {
        val isBookmarked = sessionDetailState.content.isBookmarked
        val backgroundColor by animateColorAsState(
          if (isBookmarked) AMColor.amRed else Color.White
        )
        FloatingActionButton(
          containerColor = backgroundColor,
          onClick = { onBookmarkClick(!isBookmarked) }
        ) {
          Crossfade(isBookmarked) { bookmarked ->
            Icon(
              imageVector = if (bookmarked) Icons.Rounded.BookmarkRemove else Icons.Rounded.BookmarkAdd,
              contentDescription = stringResource(Res.string.bookmarked)
            )
          }
        }
      }
    }
  ) { innerPadding ->
    when (sessionDetailState) {
      is Lce.Loading, is Lce.Error -> LoadingLayout(
        modifier = Modifier
          .padding(innerPadding)
          .consumeWindowInsets(innerPadding)
      )

      is Lce.Content -> SessionDetails(
        sessionDetails = sessionDetailState.content,
        openLink = onOpenLink,
        onApplyForAppClinic = onApplyForAppClinic,
        onSpeakerClick = onSpeakerClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        modifier = Modifier
          .padding(innerPadding)
          .consumeWindowInsets(innerPadding)
      )
    }
  }
}

@Composable
private fun SessionDetails(
  modifier: Modifier = Modifier,
  sessionDetails: SessionDetailState,
  openLink: (SocialsItem) -> Unit,
  onApplyForAppClinic: () -> Unit,
  onSpeakerClick: (speakerId: String) -> Unit,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
) {
  Column(
    modifier
      .verticalScroll(state = rememberScrollState())
  ) {
    // 1. Header section
    HeaderSection(sessionDetails)

    // 2. Video section (conditional)
    if (sessionDetails.session.videoURL.isNotEmpty()) {
      VideoSection(
        videoURL = sessionDetails.session.videoURL,
        openLink = openLink
      )
    }

    // 3. Description section
    if (!sessionDetails.session.description.isNullOrEmpty()) {
      DescriptionSection(description = sessionDetails.session.description.orEmpty())
    }

    // 4. App Clinic apply button
    if (sessionDetails.session.isAppClinic()) {
      Button(
        onClick = onApplyForAppClinic,
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .padding(top = 16.dp)
          .align(Alignment.CenterHorizontally),
      ) {
        Text(
          text = stringResource(Res.string.session_app_clinic_apply),
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Bold,
        )
      }
    }

    // 5. Speakers section
    if (sessionDetails.speakers.isNotEmpty()) {
      SpeakersSection(
        speakers = sessionDetails.speakers,
        onSpeakerClick = onSpeakerClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
      )
    }

    // 6. OpenFeedback / feedback waiting
    if (sessionDetails.session.type == "talk") {
      Column(modifier = Modifier.padding(16.dp)) {
        if (Clock.System.now() > sessionDetails.startTimestamp) {
          OpenFeedbackLayout(
            projectId = "v6kx3QuQkDU4fX0Ta989",
            sessionId = sessionDetails.session.id,
          )
        } else {
          Surface(
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, separatorColor()),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
              text = stringResource(Res.string.feedbackWaiting),
              style = MaterialTheme.typography.bodyMedium,
              textAlign = TextAlign.Center
            )
          }
        }
      }
    }

    // Bottom spacer to account for the FAB
    Spacer(modifier = Modifier.height(64.dp))
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HeaderSection(sessionDetails: SessionDetailState) {
  val session = sessionDetails.session

  Column(modifier = Modifier.padding(16.dp)) {
    // Badges row
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Duration badge
      DurationBadge(duration = session.duration)

      // Language badge
      val languageLabel = session.language.asLanguageResource()
      if (languageLabel != null) {
        LanguageBadge(
          language = languageLabel,
          emoji = EmojiUtils.getLanguageInEmoji(session.language)
        )
      }
    }

    // Title
    SelectionContainer {
      Text(
        text = session.title,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 16.dp)
      )
    }

    // Date/time + Room row with icons
    val formattedDate = formatTimeInterval(
      sessionDetails.startTimestamp.toLocalDateTime(TimeZone.currentSystemDefault()),
      sessionDetails.endTimestamp.toLocalDateTime(TimeZone.currentSystemDefault())
    )
    Row(
      modifier = Modifier.padding(top = 8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      Icon(
        imageVector = Icons.Rounded.Schedule,
        contentDescription = null,
        modifier = Modifier.size(16.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Text(
        text = formattedDate,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
      if (sessionDetails.room.name.isNotEmpty()) {
        Text(
          text = "\u2022",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(horizontal = 4.dp)
        )
        Icon(
          imageVector = Icons.Rounded.LocationOn,
          contentDescription = null,
          modifier = Modifier.size(16.dp),
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = sessionDetails.room.name,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Tags as chips
    if (session.tags.isNotEmpty() || session.complexity != null) {
      FlowRow(
        modifier = Modifier.padding(top = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        val chipBorder = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        for (tag in session.tags) {
          SuggestionChip(
            onClick = {},
            label = { Text(text = tag) },
            shape = RoundedCornerShape(17.dp),
            border = chipBorder
          )
        }
        session.complexity?.let { complexity ->
          SuggestionChip(
            onClick = {},
            label = { Text(text = complexity.name) },
            shape = RoundedCornerShape(17.dp),
            border = chipBorder
          )
        }
      }
    }
  }
}

@Composable
private fun DurationBadge(duration: Duration) {
  val minutes = duration.inWholeMinutes
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = MaterialTheme.colorScheme.primaryContainer,
  ) {
    Text(
      text = "${minutes}min",
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onPrimaryContainer
    )
  }
}

@Composable
private fun LanguageBadge(language: String, emoji: String?) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
    color = Color.Transparent
  ) {
    Text(
      text = if (emoji != null) "$language $emoji" else language,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurface
    )
  }
}

@Composable
private fun String.asLanguageResource(): String? {
  return when (this) {
    "English" -> stringResource(Res.string.english)
    "French" -> stringResource(Res.string.french)
    else -> null
  }
}

@Composable
private fun VideoSection(
  videoURL: String,
  openLink: (SocialsItem) -> Unit,
) {
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .height(192.dp)
      .clip(RoundedCornerShape(12.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .clickable { openLink(SocialsItem("video", videoURL)) },
    contentAlignment = Alignment.Center
  ) {
    Surface(
      shape = CircleShape,
      color = Color.White.copy(alpha = 0.8f),
      modifier = Modifier.size(56.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          imageVector = Icons.Rounded.PlayArrow,
          contentDescription = null,
          modifier = Modifier.size(32.dp),
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
    }
  }
}

@Composable
private fun DescriptionSection(description: String) {
  Column(modifier = Modifier.padding(16.dp)) {
    Text(
      text = stringResource(Res.string.about_this_talk),
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.SemiBold
    )
    Spacer(modifier = Modifier.height(12.dp))
    SelectionContainer {
      Text(
        text = description,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Start
      )
    }
  }
}

@Composable
private fun SpeakersSection(
  speakers: List<Speaker>,
  onSpeakerClick: (speakerId: String) -> Unit,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
) {
  Column(modifier = Modifier.padding(horizontal = 16.dp)) {
    HorizontalDivider()
    Text(
      text = stringResource(Res.string.speaker_section),
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.SemiBold,
      modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      for (speaker in speakers) {
        SpeakerCard(
          speaker = speaker,
          onClick = { onSpeakerClick(speaker.id) },
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
        )
      }
    }
  }
}

@Composable
private fun SpeakerCard(
  speaker: Speaker,
  onClick: () -> Unit,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
) {
  Card(
    onClick = onClick,
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface,
      contentColor = MaterialTheme.colorScheme.onSurface
    ),
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    shape = RoundedCornerShape(12.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Photo
      speaker.photoUrl?.let { photoUrl ->
        val photoModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
          with(sharedTransitionScope) {
            Modifier.sharedElement(
              sharedContentState = rememberSharedContentState(key = "speaker_photo_${speaker.id}"),
              animatedVisibilityScope = animatedVisibilityScope,
            )
          }
        } else {
          Modifier
        }
        AsyncImage(
          model = photoUrl,
          contentDescription = stringResource(Res.string.speakers),
          modifier = photoModifier
            .size(64.dp)
            .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
      }

      // Name + company + handle
      Column(modifier = Modifier.weight(1f)) {
        speaker.name?.let { name ->
          Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
        }
        speaker.company?.let { company ->
          if (company.isNotBlank()) {
            Text(
              text = company,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
        // Show first social handle if available
        speaker.socials?.filterNotNull()?.firstOrNull()?.let { social ->
          val handle = extractHandle(social)
          if (handle != null) {
            Text(
              text = "@$handle",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.primary
            )
          }
        }
      }

      // Chevron
      Icon(
        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

private fun extractHandle(social: SocialsItem): String? {
  val url = social.url ?: return null
  // Try to extract a handle from the URL (last path segment)
  val trimmed = url.trimEnd('/')
  val lastSegment = trimmed.substringAfterLast('/')
  return lastSegment.removePrefix("@").ifEmpty { null }
}

@Preview
@Composable
private fun SessionDetailLayoutLoadingPreview() {
  SessionDetailLayout(
    sessionDetailState = Lce.Loading,
    onBackClick = {},
    onBookmarkClick = {},
    onShareSession = {},
    onOpenLink = {},
    onApplyForAppClinic = {},
    onSpeakerClick = {}
  )
}
