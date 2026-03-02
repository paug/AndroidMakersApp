package com.androidmakers.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Gavel
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.QuestionAnswer
import androidx.compose.material.icons.rounded.SettingsBrightness
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.theme.LocalIsDarkTheme
import com.androidmakers.ui.theme.LocalIsNeobrutalism
import com.androidmakers.ui.theme.neoBrutalBorder
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.ThemePreference
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.about
import fr.paug.androidmakers.ui.about_android_makers
import fr.paug.androidmakers.ui.about_event_tagline
import fr.paug.androidmakers.ui.about_links
import fr.paug.androidmakers.ui.code_of_conduct
import fr.paug.androidmakers.ui.faq
import fr.paug.androidmakers.ui.github_repo
import fr.paug.androidmakers.ui.ic_network_bluesky
import fr.paug.androidmakers.ui.ic_network_x
import fr.paug.androidmakers.ui.ic_network_youtube
import fr.paug.androidmakers.ui.logo_android_makers
import fr.paug.androidmakers.ui.settings
import fr.paug.androidmakers.ui.social
import fr.paug.androidmakers.ui.theme
import fr.paug.androidmakers.ui.theme_dark
import fr.paug.androidmakers.ui.theme_light
import fr.paug.androidmakers.ui.theme_neobrutalism
import fr.paug.androidmakers.ui.theme_system
import fr.paug.androidmakers.ui.version
import fr.paug.androidmakers.ui.x_hashtag
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AboutScreen(
    versionName: String,
    versionCode: String,
) {
  val viewModel = koinViewModel<AboutViewModel>()
  val urlOpener = LocalUriHandler.current.toUrlOpener()
  val themePreference by viewModel.themePreference.collectAsStateWithLifecycle()

  Column(
      modifier = Modifier
          .fillMaxSize()
          .verticalScroll(state = rememberScrollState())
          .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    HeroSection()

    AboutCard()

    LinksCard(
        onFaqClick = { viewModel.openFaq(urlOpener) },
        onCocClick = { viewModel.openCoc(urlOpener) },
        onGithubRepoClick = { viewModel.openGithubRepo(urlOpener) }
    )

    SettingsCard(
      themePreference = themePreference,
      onThemePreferenceChange = { viewModel.setThemePreference(it) }
    )

    SocialCard(
        onXHashtagClick = { viewModel.openXHashtag(urlOpener) },
        onBlueskyLogoClick = { viewModel.openBlueSkyAccount(urlOpener) },
        onXLogoClick = { viewModel.openXAccount(urlOpener) },
        onYouTubeLogoClick = { viewModel.openYoutube(urlOpener) }
    )

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(Res.string.version, versionName, versionCode),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@Composable
private fun HeroSection() {
  Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
        modifier = Modifier
            .heightIn(max = 96.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        painter = painterResource(Res.drawable.logo_android_makers),
        contentDescription = "Android Makers"
    )
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = stringResource(Res.string.about_event_tagline),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
  }
}

@Composable
private fun SectionHeader(title: String) {
  Text(
      modifier = Modifier.padding(bottom = 8.dp),
      text = title,
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurface
  )
}

@Composable
private fun AboutCard() {
  Surface(
      modifier = Modifier.fillMaxWidth().neoBrutalElevation(),
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.surfaceContainerHigh
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      SectionHeader(title = stringResource(Res.string.about))
      Text(
          text = stringResource(Res.string.about_android_makers),
          style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
          color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}

@Composable
private fun LinksCard(
    onFaqClick: () -> Unit,
    onCocClick: () -> Unit,
    onGithubRepoClick: () -> Unit
) {
  Surface(
      modifier = Modifier.fillMaxWidth().neoBrutalElevation(),
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.surfaceContainerHigh
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      SectionHeader(title = stringResource(Res.string.about_links))
      LinkRow(
          icon = Icons.Rounded.QuestionAnswer,
          label = stringResource(Res.string.faq),
          onClick = onFaqClick
      )
      HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
      LinkRow(
          icon = Icons.Rounded.Gavel,
          label = stringResource(Res.string.code_of_conduct),
          onClick = onCocClick
      )
      HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
      LinkRow(
          icon = Icons.Rounded.Code,
          label = stringResource(Res.string.github_repo),
          onClick = onGithubRepoClick
      )
    }
  }
}

@Composable
private fun LinkRow(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
  Row(
      modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = onClick)
          .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(24.dp)
    )
    Text(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 16.dp),
        text = label,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
    )
    Icon(
        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@Composable
private fun SocialCard(
    onXHashtagClick: () -> Unit,
    onBlueskyLogoClick: () -> Unit,
    onXLogoClick: () -> Unit,
    onYouTubeLogoClick: () -> Unit
) {
  val darkMode = LocalIsDarkTheme.current

  Surface(
      modifier = Modifier.fillMaxWidth().neoBrutalElevation(),
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.surfaceContainerHigh
  ) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
      SectionHeader(title = stringResource(Res.string.social))

      Box(
          modifier = Modifier
              .clip(MaterialTheme.shapes.small)
              .background(MaterialTheme.colorScheme.primaryContainer)
              .clickable(onClick = onXHashtagClick)
              .padding(horizontal = 16.dp, vertical = 8.dp)
      ) {
        Text(
            text = stringResource(Res.string.x_hashtag),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
      }

      Row(
          modifier = Modifier.padding(top = 16.dp),
          horizontalArrangement = Arrangement.spacedBy(24.dp)
      ) {
        SocialIconWithLabel(
            onClick = onBlueskyLogoClick,
            label = "Bluesky"
        ) {
          Icon(
              modifier = Modifier.size(24.dp),
              painter = painterResource(Res.drawable.ic_network_bluesky),
              contentDescription = "Bluesky"
          )
        }
        SocialIconWithLabel(
            onClick = onXLogoClick,
            label = "X"
        ) {
          Icon(
              modifier = Modifier.size(24.dp),
              painter = painterResource(Res.drawable.ic_network_x),
              tint = if (darkMode) Color.White else Color.Black,
              contentDescription = "X"
          )
        }
        SocialIconWithLabel(
            onClick = onYouTubeLogoClick,
            label = "YouTube"
        ) {
          Image(
              modifier = Modifier.size(24.dp),
              painter = painterResource(Res.drawable.ic_network_youtube),
              contentDescription = "YouTube"
          )
        }
      }
    }
  }
}

@Composable
private fun SocialIconWithLabel(
    onClick: () -> Unit,
    label: String,
    content: @Composable () -> Unit,
) {
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(4.dp)
  ) {
    val circularShape = if (LocalIsNeobrutalism.current) RectangleShape else CircleShape
    Surface(
        modifier = Modifier.size(56.dp).neoBrutalBorder(),
        shape = circularShape,
        color = MaterialTheme.colorScheme.surfaceContainer,
        onClick = onClick
    ) {
      Box(contentAlignment = Alignment.Center) {
        content()
      }
    }
    Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsCard(
    themePreference: ThemePreference,
    onThemePreferenceChange: (ThemePreference) -> Unit
) {
  Surface(
      modifier = Modifier.fillMaxWidth().neoBrutalElevation(),
      shape = MaterialTheme.shapes.large,
      color = MaterialTheme.colorScheme.surfaceContainerHigh
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      SectionHeader(title = stringResource(Res.string.settings))
      Text(
          text = stringResource(Res.string.theme),
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(bottom = 8.dp)
      )
      val options = ThemePreference.entries
      val icons = mapOf(
          ThemePreference.System to Icons.Rounded.SettingsBrightness,
          ThemePreference.Light to Icons.Rounded.LightMode,
          ThemePreference.Dark to Icons.Rounded.DarkMode,
          ThemePreference.Neobrutalism to Icons.Rounded.AutoAwesome,
      )
      val labels = mapOf(
          ThemePreference.System to stringResource(Res.string.theme_system),
          ThemePreference.Light to stringResource(Res.string.theme_light),
          ThemePreference.Dark to stringResource(Res.string.theme_dark),
          ThemePreference.Neobrutalism to stringResource(Res.string.theme_neobrutalism),
      )
      SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
        options.forEachIndexed { index, option ->
          SegmentedButton(
              selected = themePreference == option,
              onClick = { onThemePreferenceChange(option) },
              shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
              icon = {},
          ) {
            Icon(
                imageVector = icons[option]!!,
                contentDescription = labels[option],
                modifier = Modifier.size(20.dp),
            )
          }
        }
      }
    }
  }
}
