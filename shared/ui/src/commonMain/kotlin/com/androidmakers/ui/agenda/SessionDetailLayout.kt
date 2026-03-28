@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.androidmakers.ui.agenda

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.androidmakers.ui.common.EmojiUtils
import com.androidmakers.ui.common.LoadingLayout
import com.androidmakers.ui.common.GraphQLFeedbackLayout
import com.androidmakers.ui.common.separatorColor
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.getPlatformContext
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.SessionDetailState
import com.androidmakers.ui.theme.AMColor
import com.androidmakers.ui.theme.LocalIsNeobrutalism
import com.androidmakers.ui.theme.neoBrutalBorder
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.Complexity
import fr.androidmakers.domain.model.SocialsItem
import fr.androidmakers.domain.model.Speaker
import fr.androidmakers.domain.model.isAppClinic
import fr.androidmakers.domain.utils.formatTimeInterval
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.ic_arrow_back
import fr.paug.androidmakers.ui.ic_bookmark_add
import fr.paug.androidmakers.ui.ic_bookmark_added
import fr.paug.androidmakers.ui.ic_info
import fr.paug.androidmakers.ui.ic_keyboard_arrow_right
import fr.paug.androidmakers.ui.ic_location_on
import fr.paug.androidmakers.ui.ic_person
import fr.paug.androidmakers.ui.ic_play_arrow
import fr.paug.androidmakers.ui.ic_schedule
import fr.paug.androidmakers.ui.ic_share
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
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.Duration

/**
 * Custom colors with no M3 equivalent — badges, gradients, and status indicators.
 */
private data class SessionDetailCustomColors(
  val durationBadgeBg: Color,
  val durationBadgeText: Color,
  val languageBadgeBg: Color,
  val languageBadgeBorder: Color,
  val languageBadgeText: Color,
  val videoGradientStart: Color,
  val videoGradientEnd: Color,
  val onlineDot: Color,
)

private val LightCustomColors = SessionDetailCustomColors(
  durationBadgeBg = Color(0xFFE8F5E9),
  durationBadgeText = Color(0xFF166534),
  languageBadgeBg = Color(0xFFFEF2F2),
  languageBadgeBorder = Color(0xFFFEE2E2),
  languageBadgeText = Color(0xFFDC2626),
  videoGradientStart = Color(0xFFF0FDF4),
  videoGradientEnd = Color(0xFFECFDF5),
  onlineDot = Color(0xFF22C55E),
)

private val DarkCustomColors = SessionDetailCustomColors(
  durationBadgeBg = Color(0xFF1B3A26),
  durationBadgeText = Color(0xFF86EFAC),
  languageBadgeBg = Color(0xFF3B1212),
  languageBadgeBorder = Color(0xFF5C1D1D),
  languageBadgeText = Color(0xFFFCA5A5),
  videoGradientStart = Color(0xFF1A2E1F),
  videoGradientEnd = Color(0xFF162B1B),
  onlineDot = Color(0xFF22C55E),
)

@Composable
private fun sessionDetailCustomColors(): SessionDetailCustomColors {
  return if (isSystemInDarkTheme()) DarkCustomColors else LightCustomColors
}

@Composable
fun SessionDetailScreen(
  viewModel: SessionDetailViewModel,
  onBackClick: () -> Unit,
  onSpeakerClick: (speakerId: String) -> Unit,
  showBackButton: Boolean = true,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
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
    showBackButton = showBackButton,
    sharedTransitionScope = sharedTransitionScope,
    animatedVisibilityScope = animatedVisibilityScope,
  )
}

@Suppress("LongParameterList")
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
  showBackButton: Boolean = true,
  sharedTransitionScope: SharedTransitionScope? = null,
  animatedVisibilityScope: AnimatedVisibilityScope? = null,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    containerColor = MaterialTheme.colorScheme.background,
    topBar = {
      SessionDetailTopAppBar(
        scrollBehavior = scrollBehavior,
        showBackButton = showBackButton,
        onBackClick = onBackClick,
        hasContent = sessionDetailState is Lce.Content,
        onShareSession = onShareSession,
      )
    },
    floatingActionButton = {
      if (sessionDetailState is Lce.Content) {
        SessionDetailFab(
          isBookmarked = sessionDetailState.content.isBookmarked,
          onBookmarkClick = onBookmarkClick,
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SessionDetailTopAppBar(
  scrollBehavior: TopAppBarScrollBehavior,
  showBackButton: Boolean,
  onBackClick: () -> Unit,
  hasContent: Boolean,
  onShareSession: () -> Unit,
) {
  TopAppBar(
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.background,
      scrolledContainerColor = MaterialTheme.colorScheme.background,
    ),
    navigationIcon = {
      if (showBackButton) {
        IconButton(onClick = onBackClick) {
          Icon(
            painter = painterResource(Res.drawable.ic_arrow_back),
            contentDescription = stringResource(Res.string.back)
          )
        }
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
      if (hasContent) {
        IconButton(onClick = onShareSession) {
          Icon(
            painter = painterResource(Res.drawable.ic_share),
            contentDescription = stringResource(Res.string.share)
          )
        }
      }
    },
    scrollBehavior = scrollBehavior
  )
}

@Composable
private fun SessionDetailFab(
  isBookmarked: Boolean,
  onBookmarkClick: (Boolean) -> Unit,
) {
  val backgroundColor by animateColorAsState(
    if (isBookmarked) AMColor.amRed else MaterialTheme.colorScheme.surfaceContainerHighest
  )
  FloatingActionButton(
    modifier = Modifier.neoBrutalElevation(shadowOffset = 2.dp),
    containerColor = backgroundColor,
    onClick = { onBookmarkClick(!isBookmarked) }
  ) {
    Crossfade(isBookmarked) { bookmarked ->
      Icon(
        painter = painterResource(if (bookmarked) Res.drawable.ic_bookmark_added else Res.drawable.ic_bookmark_add),
        contentDescription = stringResource(Res.string.bookmarked)
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
    // 1. Header card
    HeaderCard(sessionDetails)

    // 2. Video section (conditional)
    if (sessionDetails.session.videoURL.isNotEmpty()) {
      Spacer(modifier = Modifier.height(16.dp))
      VideoSection(
        videoURL = sessionDetails.session.videoURL,
        openLink = openLink
      )
    }

    // 3. Description section
    if (!sessionDetails.session.description.isNullOrEmpty()) {
      Spacer(modifier = Modifier.height(16.dp))
      DescriptionSection(description = sessionDetails.session.description.orEmpty())
    }

    // 4. App Clinic apply button
    if (sessionDetails.session.isAppClinic()) {
      FilledTonalButton(
        onClick = onApplyForAppClinic,
        modifier = Modifier
          .padding(horizontal = 16.dp)
          .padding(top = 16.dp)
          .align(Alignment.CenterHorizontally)
          .neoBrutalElevation(shadowOffset = 2.dp),
      ) {
        Text(
          text = stringResource(Res.string.session_app_clinic_apply),
          style = MaterialTheme.typography.bodyLargeEmphasized,
        )
      }
    }

    // 5. Speakers section
    if (sessionDetails.speakers.isNotEmpty()) {
      Spacer(modifier = Modifier.height(16.dp))
      SpeakersSection(
        speakers = sessionDetails.speakers,
        onSpeakerClick = onSpeakerClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
      )
    }

    // 6. Feedback section
    if (sessionDetails.session.type == "talk") {
      Spacer(modifier = Modifier.height(16.dp))
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (Clock.System.now() > sessionDetails.startTimestamp) {
          GraphQLFeedbackLayout(sessionId = sessionDetails.session.id)
        } else {
          OutlinedCard(
            shape = MaterialTheme.shapes.large,
            border = BorderStroke(1.dp, separatorColor()),
            modifier = Modifier.fillMaxWidth().neoBrutalElevation()
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
    Spacer(modifier = Modifier.height(96.dp))
  }
}

@Composable
private fun HeaderCard(sessionDetails: SessionDetailState) {
  val session = sessionDetails.session

  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .neoBrutalElevation(),
    shape = MaterialTheme.shapes.large,
    colors = CardDefaults.elevatedCardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    ),
  ) {
    Column(modifier = Modifier.padding(25.dp)) {
      SessionHeaderBadgesRow(
        duration = session.duration,
        language = session.language,
      )

      // Title
      SelectionContainer {
        Text(
          text = session.title,
          style = MaterialTheme.typography.headlineSmallEmphasized,
          color = MaterialTheme.colorScheme.onSurface,
          modifier = Modifier.padding(top = 16.dp)
        )
      }

      val formattedDate = formatTimeInterval(
        sessionDetails.startTimestamp.toLocalDateTime(TimeZone.currentSystemDefault()),
        sessionDetails.endTimestamp.toLocalDateTime(TimeZone.currentSystemDefault())
      )
      SessionHeaderDateRoomRow(
        formattedDate = formattedDate,
        roomName = sessionDetails.room.name,
      )

      SessionHeaderTagsRow(
        tags = session.tags,
        complexity = session.complexity,
      )
    }
  }
}

@Composable
private fun SessionHeaderBadgesRow(
  duration: Duration,
  language: String,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    DurationBadge(duration = duration)

    val languageLabel = language.asLanguageResource()
    if (languageLabel != null) {
      LanguageBadge(
        language = languageLabel,
        emoji = EmojiUtils.getLanguageInEmoji(language)
      )
    }
  }
}

@Composable
private fun SessionHeaderDateRoomRow(
  formattedDate: String,
  roomName: String,
) {
  FlowRow(
    modifier = Modifier.padding(top = 8.dp),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    Row(
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        painter = painterResource(Res.drawable.ic_schedule),
        contentDescription = null,
        modifier = Modifier.size(16.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
      Text(
        text = formattedDate,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
    if (roomName.isNotEmpty()) {
      Text(
        text = "\u2022",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 2.dp)
      )
      Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          painter = painterResource(Res.drawable.ic_location_on),
          contentDescription = null,
          modifier = Modifier.size(16.dp),
          tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = roomName,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SessionHeaderTagsRow(
  tags: List<String>,
  complexity: Complexity?,
) {
  if (tags.isNotEmpty() || complexity != null) {
    FlowRow(
      modifier = Modifier.padding(top = 8.dp),
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      for (tag in tags) {
        TagChip(text = tag)
      }
      complexity?.let {
        TagChip(text = it.name)
      }
    }
  }
}

@Composable
private fun TagChip(text: String) {
  SuggestionChip(
    onClick = {},
    label = {
      Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelMedium.copy(
          letterSpacing = 0.6.sp
        ),
      )
    },
    shape = MaterialTheme.shapes.small,
    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    modifier = Modifier.neoBrutalBorder(),
    colors = SuggestionChipDefaults.suggestionChipColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
      labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
    ),
  )
}

@Composable
private fun DurationBadge(duration: Duration) {
  val customColors = sessionDetailCustomColors()
  val minutes = duration.inWholeMinutes
  Surface(
    modifier = Modifier.neoBrutalBorder(),
    shape = MaterialTheme.shapes.medium,
    color = customColors.durationBadgeBg,
  ) {
    Text(
      text = "${minutes}min",
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
      color = customColors.durationBadgeText
    )
  }
}

@Composable
private fun LanguageBadge(language: String, emoji: String?) {
  val customColors = sessionDetailCustomColors()
  Surface(
    modifier = Modifier.neoBrutalBorder(),
    shape = MaterialTheme.shapes.medium,
    border = BorderStroke(1.dp, customColors.languageBadgeBorder),
    color = customColors.languageBadgeBg
  ) {
    Text(
      text = if (emoji != null) "$language $emoji" else language,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
      color = customColors.languageBadgeText
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
  val customColors = sessionDetailCustomColors()
  val circularShape = if (LocalIsNeobrutalism.current) RectangleShape else CircleShape
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .height(192.dp)
      .clip(MaterialTheme.shapes.large)
      .background(
        Brush.linearGradient(
          colors = listOf(
            customColors.videoGradientStart,
            customColors.videoGradientEnd
          )
        )
      )
      .clickable { openLink(SocialsItem("video", videoURL)) },
    contentAlignment = Alignment.Center
  ) {
    Surface(
      shape = circularShape,
      color = Color.White.copy(alpha = 0.8f),
      modifier = Modifier.size(56.dp)
    ) {
      Box(contentAlignment = Alignment.Center) {
        Icon(
          painter = painterResource(Res.drawable.ic_play_arrow),
          contentDescription = null,
          modifier = Modifier.size(32.dp),
          tint = MaterialTheme.colorScheme.onSurface
        )
      }
    }
  }
}

@Composable
private fun SectionHeader(icon: Painter, title: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(6.dp),
    modifier = Modifier.padding(bottom = 12.dp)
  ) {
    Icon(
      painter = icon,
      contentDescription = null,
      modifier = Modifier.size(16.dp),
      tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
    Text(
      text = title.uppercase(),
      style = MaterialTheme.typography.labelMediumEmphasized.copy(
        letterSpacing = 0.6.sp
      ),
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@Composable
private fun DescriptionSection(description: String) {
  Column(modifier = Modifier.padding(horizontal = 16.dp)) {
    SectionHeader(
      icon = painterResource(Res.drawable.ic_info),
      title = stringResource(Res.string.about_this_talk)
    )
    ElevatedCard(
      modifier = Modifier.neoBrutalElevation(),
      shape = MaterialTheme.shapes.large,
      colors = CardDefaults.elevatedCardColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
      ),
    ) {
      SelectionContainer {
        Text(
          text = description,
          modifier = Modifier.padding(25.dp),
          style = MaterialTheme.typography.bodyMedium.copy(
            lineHeight = 22.75.sp,
            hyphens = Hyphens.Auto,
            lineBreak = LineBreak.Paragraph,
          ),
          color = MaterialTheme.colorScheme.onSurface,
          textAlign = TextAlign.Start
        )
      }
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
    SectionHeader(
      icon = painterResource(Res.drawable.ic_person),
      title = pluralStringResource(Res.plurals.speaker_section, speakers.size)
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
  ElevatedCard(
    modifier = Modifier.neoBrutalElevation(),
    onClick = onClick,
    shape = MaterialTheme.shapes.large,
    colors = CardDefaults.elevatedCardColors(
      containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
    ),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      speaker.photoUrl?.let { photoUrl ->
        SpeakerPhotoWithDot(
          photoUrl = photoUrl,
          speakerId = speaker.id,
          sharedTransitionScope = sharedTransitionScope,
          animatedVisibilityScope = animatedVisibilityScope,
        )
        Spacer(modifier = Modifier.width(16.dp))
      }

      SpeakerTextInfo(
        speaker = speaker,
        modifier = Modifier.weight(1f),
      )

      // Chevron
      Icon(
        painter = painterResource(Res.drawable.ic_keyboard_arrow_right),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun SpeakerPhotoWithDot(
  photoUrl: String,
  speakerId: String,
  sharedTransitionScope: SharedTransitionScope?,
  animatedVisibilityScope: AnimatedVisibilityScope?,
) {
  val customColors = sessionDetailCustomColors()
  val circularShape = if (LocalIsNeobrutalism.current) RectangleShape else CircleShape
  val photoModifier = if (sharedTransitionScope != null && animatedVisibilityScope != null) {
    with(sharedTransitionScope) {
      Modifier.sharedElement(
        sharedContentState = rememberSharedContentState(key = "speaker_photo_$speakerId"),
        animatedVisibilityScope = animatedVisibilityScope,
      )
    }
  } else {
    Modifier
  }
  Box {
    AsyncImage(
      model = photoUrl,
      contentDescription = stringResource(Res.string.speakers),
      modifier = photoModifier
        .size(56.dp)
        .clip(circularShape)
        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), circularShape)
    )
    // Green online dot
    Box(
      modifier = Modifier
        .size(14.dp)
        .align(Alignment.BottomEnd)
        .background(MaterialTheme.colorScheme.surfaceContainerHigh, circularShape)
        .padding(2.dp)
        .background(customColors.onlineDot, circularShape)
    )
  }
}

@Composable
private fun SpeakerTextInfo(
  speaker: Speaker,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    speaker.name?.let { name ->
      Text(
        text = name,
        style = MaterialTheme.typography.bodyLargeEmphasized,
        color = MaterialTheme.colorScheme.onSurface
      )
    }
    speaker.company?.let { company ->
      if (company.isNotBlank()) {
        Text(
          text = company,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
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
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
          color = MaterialTheme.colorScheme.outline
        )
      }
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
