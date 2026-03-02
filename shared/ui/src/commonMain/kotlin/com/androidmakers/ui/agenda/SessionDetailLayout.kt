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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.BookmarkAdd
import androidx.compose.material.icons.rounded.BookmarkRemove
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

private data class SessionDetailColorScheme(
  val screenBackground: Color,
  val cardBackground: Color,
  val durationBadgeBg: Color,
  val durationBadgeText: Color,
  val languageBadgeBg: Color,
  val languageBadgeBorder: Color,
  val languageBadgeText: Color,
  val titleText: Color,
  val metaText: Color,
  val tagBg: Color,
  val tagBorder: Color,
  val tagText: Color,
  val descriptionText: Color,
  val videoGradientStart: Color,
  val videoGradientEnd: Color,
  val speakerName: Color,
  val speakerCompany: Color,
  val speakerHandle: Color,
  val onlineDot: Color,
  val photoBorder: Color,
)

private val LightSessionDetailColors = SessionDetailColorScheme(
  screenBackground = Color(0xFFF2F2F7),
  cardBackground = Color.White,
  durationBadgeBg = Color(0xFFE8F5E9),
  durationBadgeText = Color(0xFF166534),
  languageBadgeBg = Color(0xFFFEF2F2),
  languageBadgeBorder = Color(0xFFFEE2E2),
  languageBadgeText = Color(0xFFDC2626),
  titleText = Color(0xFF111827),
  metaText = Color(0xFF6B7280),
  tagBg = Color(0xFFF9FAFB),
  tagBorder = Color(0xFFF3F4F6),
  tagText = Color(0xFF4B5563),
  descriptionText = Color(0xFF4B5563),
  videoGradientStart = Color(0xFFF0FDF4),
  videoGradientEnd = Color(0xFFECFDF5),
  speakerName = Color(0xFF111827),
  speakerCompany = Color(0xFF6B7280),
  speakerHandle = Color(0xFF9CA3AF),
  onlineDot = Color(0xFF22C55E),
  photoBorder = Color(0xFF6B7280).copy(alpha = 0.2f),
)

private val DarkSessionDetailColors = SessionDetailColorScheme(
  screenBackground = Color(0xFF1C1B1F),
  cardBackground = Color(0xFF2B2930),
  durationBadgeBg = Color(0xFF1B3A26),
  durationBadgeText = Color(0xFF86EFAC),
  languageBadgeBg = Color(0xFF3B1212),
  languageBadgeBorder = Color(0xFF5C1D1D),
  languageBadgeText = Color(0xFFFCA5A5),
  titleText = Color(0xFFE6E1E5),
  metaText = Color(0xFF9CA3AF),
  tagBg = Color(0xFF2B2930),
  tagBorder = Color(0xFF3F3F46),
  tagText = Color(0xFF9CA3AF),
  descriptionText = Color(0xFF9CA3AF),
  videoGradientStart = Color(0xFF1A2E1F),
  videoGradientEnd = Color(0xFF162B1B),
  speakerName = Color(0xFFE6E1E5),
  speakerCompany = Color(0xFF9CA3AF),
  speakerHandle = Color(0xFF6B7280),
  onlineDot = Color(0xFF22C55E),
  photoBorder = Color(0xFF9CA3AF).copy(alpha = 0.2f),
)

@Composable
private fun sessionDetailColors(): SessionDetailColorScheme {
  return if (isSystemInDarkTheme()) DarkSessionDetailColors else LightSessionDetailColors
}

@Composable
fun SessionDetailScreen(
  viewModel: SessionDetailViewModel,
  onBackClick: () -> Unit,
  onSpeakerClick: (speakerId: String) -> Unit,
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
  val colors = sessionDetailColors()
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    containerColor = colors.screenBackground,
    topBar = {
      TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background,
          scrolledContainerColor = MaterialTheme.colorScheme.background,
        ),
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
      Spacer(modifier = Modifier.height(16.dp))
      SpeakersSection(
        speakers = sessionDetails.speakers,
        onSpeakerClick = onSpeakerClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
      )
    }

    // 6. OpenFeedback / feedback waiting
    if (sessionDetails.session.type == "talk") {
      Spacer(modifier = Modifier.height(16.dp))
      Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        if (Clock.System.now() > sessionDetails.startTimestamp) {
          OpenFeedbackLayout(
            projectId = "v6kx3QuQkDU4fX0Ta989",
            sessionId = sessionDetails.session.id,
          )
        } else {
          Surface(
            shape = RoundedCornerShape(16.dp),
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
private fun HeaderCard(sessionDetails: SessionDetailState) {
  val session = sessionDetails.session
  val colors = sessionDetailColors()

  Surface(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    shape = RoundedCornerShape(16.dp),
    color = colors.cardBackground
  ) {
    Column(modifier = Modifier.padding(25.dp)) {
      // Badges row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        DurationBadge(duration = session.duration)

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
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          color = colors.titleText,
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
          tint = colors.metaText
        )
        Text(
          text = formattedDate,
          style = MaterialTheme.typography.bodySmall,
          color = colors.metaText
        )
        if (sessionDetails.room.name.isNotEmpty()) {
          Text(
            text = "\u2022",
            style = MaterialTheme.typography.bodySmall,
            color = colors.metaText,
            modifier = Modifier.padding(horizontal = 4.dp)
          )
          Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = colors.metaText
          )
          Text(
            text = sessionDetails.room.name,
            style = MaterialTheme.typography.bodySmall,
            color = colors.metaText
          )
        }
      }

      // Tags
      if (session.tags.isNotEmpty() || session.complexity != null) {
        FlowRow(
          modifier = Modifier.padding(top = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          for (tag in session.tags) {
            TagChip(text = tag)
          }
          session.complexity?.let { complexity ->
            TagChip(text = complexity.name)
          }
        }
      }
    }
  }
}

@Composable
private fun TagChip(text: String) {
  val colors = sessionDetailColors()
  Surface(
    shape = RoundedCornerShape(8.dp),
    color = colors.tagBg,
    modifier = Modifier.border(1.dp, colors.tagBorder, RoundedCornerShape(8.dp))
  ) {
    Text(
      text = text.uppercase(),
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
      style = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.6.sp
      ),
      color = colors.tagText
    )
  }
}

@Composable
private fun DurationBadge(duration: Duration) {
  val colors = sessionDetailColors()
  val minutes = duration.inWholeMinutes
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = colors.durationBadgeBg,
  ) {
    Text(
      text = "${minutes}min",
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
      color = colors.durationBadgeText
    )
  }
}

@Composable
private fun LanguageBadge(language: String, emoji: String?) {
  val colors = sessionDetailColors()
  Surface(
    shape = RoundedCornerShape(12.dp),
    border = BorderStroke(1.dp, colors.languageBadgeBorder),
    color = colors.languageBadgeBg
  ) {
    Text(
      text = if (emoji != null) "$language $emoji" else language,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
      style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
      color = colors.languageBadgeText
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
  val colors = sessionDetailColors()
  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp)
      .height(192.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(
        Brush.linearGradient(
          colors = listOf(
            colors.videoGradientStart,
            colors.videoGradientEnd
          )
        )
      )
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
private fun SectionHeader(icon: ImageVector, title: String) {
  val colors = sessionDetailColors()
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(6.dp),
    modifier = Modifier.padding(bottom = 12.dp)
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      modifier = Modifier.size(16.dp),
      tint = colors.metaText
    )
    Text(
      text = title.uppercase(),
      style = MaterialTheme.typography.labelMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        letterSpacing = 0.6.sp
      ),
      color = colors.metaText
    )
  }
}

@Composable
private fun DescriptionSection(description: String) {
  val colors = sessionDetailColors()
  Column(modifier = Modifier.padding(horizontal = 16.dp)) {
    SectionHeader(
      icon = Icons.Rounded.Info,
      title = stringResource(Res.string.about_this_talk)
    )
    Surface(
      shape = RoundedCornerShape(16.dp),
      color = colors.cardBackground
    ) {
      SelectionContainer {
        Text(
          text = description,
          modifier = Modifier.padding(25.dp),
          style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.75.sp),
          color = colors.descriptionText,
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
      icon = Icons.Rounded.Person,
      title = stringResource(Res.string.speaker_section)
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
  val colors = sessionDetailColors()
  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(16.dp),
    color = colors.cardBackground
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Photo with green online dot
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
        Box {
          AsyncImage(
            model = photoUrl,
            contentDescription = stringResource(Res.string.speakers),
            modifier = photoModifier
              .size(56.dp)
              .clip(CircleShape)
              .border(1.dp, colors.photoBorder, CircleShape)
          )
          // Green online dot
          Box(
            modifier = Modifier
              .size(14.dp)
              .align(Alignment.BottomEnd)
              .background(colors.cardBackground, CircleShape)
              .padding(2.dp)
              .background(colors.onlineDot, CircleShape)
          )
        }
        Spacer(modifier = Modifier.width(16.dp))
      }

      // Name + company + handle
      Column(modifier = Modifier.weight(1f)) {
        speaker.name?.let { name ->
          Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            fontWeight = FontWeight.Bold,
            color = colors.speakerName
          )
        }
        speaker.company?.let { company ->
          if (company.isNotBlank()) {
            Text(
              text = company,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
              color = colors.speakerCompany
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
              color = colors.speakerHandle
            )
          }
        }
      }

      // Chevron
      Icon(
        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
        contentDescription = null,
        tint = colors.metaText
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
