package com.androidmakers.ui.info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidmakers.ui.about.AboutCard
import com.androidmakers.ui.about.AboutViewModel
import com.androidmakers.ui.about.HeroSection
import com.androidmakers.ui.about.LinksCard
import com.androidmakers.ui.about.SettingsCard
import com.androidmakers.ui.about.SocialCard
import com.androidmakers.ui.common.toUrlOpener
import com.androidmakers.ui.theme.neoBrutalElevation
import com.androidmakers.ui.venue.VenuePager
import fr.androidmakers.domain.model.FeatureFlags
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.version
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InfoScreen(
  versionName: String,
  versionCode: String,
  featureFlags: FeatureFlags,
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

    if (featureFlags.venue) {
      Surface(
        modifier = Modifier.fillMaxWidth().neoBrutalElevation(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
      ) {
        VenuePager(
          modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
        )
      }
    }

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

    val showDebugInfo by viewModel.showDebugInfo.collectAsStateWithLifecycle()
    var versionTapCount by remember { mutableIntStateOf(0) }

    Text(
      modifier = Modifier
        .fillMaxWidth()
        .clickable {
          if (!showDebugInfo) {
            versionTapCount++
            if (versionTapCount >= 3) {
              viewModel.setShowDebugInfo(true)
            }
          }
        }
        .padding(8.dp),
      textAlign = TextAlign.Center,
      text = stringResource(Res.string.version, versionName, versionCode),
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    if (showDebugInfo) {
      Text(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        text = "UID: ${viewModel.userUid ?: "Not signed in"}",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
      )
    }
  }
}
