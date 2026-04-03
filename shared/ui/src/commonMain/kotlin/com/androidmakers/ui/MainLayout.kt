package com.androidmakers.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.common.navigation.AVALayout
import com.androidmakers.ui.common.navigation.AgendaKey
import com.androidmakers.ui.common.navigation.FeedKey
import com.androidmakers.ui.common.navigation.InfoKey
import com.androidmakers.ui.common.navigation.Navigator
import com.androidmakers.ui.common.navigation.SpeakersKey
import com.androidmakers.ui.common.navigation.SponsorsKey
import com.androidmakers.ui.common.navigation.parseDeepLink
import com.androidmakers.ui.common.navigation.rememberNavigationState
import com.androidmakers.ui.theme.AndroidMakersTheme
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.model.FeatureFlags
import fr.androidmakers.domain.model.ThemePreference
import fr.androidmakers.domain.repo.FeatureFlagsRepository
import fr.androidmakers.domain.repo.ThemeRepository
import org.koin.compose.koinInject


/**
 * The main layout: entry point of the application
 */
@Composable
fun MainLayout(
  versionCode: String,
  versionName: String,
  signinCallbacks: SigninCallbacks,
  deeplink: String? = null,
  featureFlagsRepository: FeatureFlagsRepository = koinInject(),
) {
  setSingletonImageLoaderFactory { context ->
    ImageLoader.Builder(context)
      .crossfade(true)
      .build()
  }

  val themeRepository = koinInject<ThemeRepository>()
  val themePreference by themeRepository.themePreference.collectAsState(ThemePreference.System)

  AndroidMakersTheme(themePreference = themePreference) {

    val startRoute = AgendaKey
    val topLevelRoutes = buildSet {
      add(FeedKey)
      add(AgendaKey)
      add(SpeakersKey)
      add(SponsorsKey)
      add(InfoKey)
    }

    val navigationState = rememberNavigationState(
      startRoute = startRoute,
      topLevelRoutes = topLevelRoutes
    )

    val navigator = remember { Navigator(navigationState) }

    LaunchedEffect(deeplink) {
      deeplink?.let { uri ->
        parseDeepLink(uri)?.let { result ->
          navigator.navigateFromDeepLink(result.tabKey, result.detailKey)
        }
      }
    }

    val featureFlags = remember { mutableStateOf<FeatureFlags?>(null) }
    LaunchedEffect(Unit) {
      featureFlags.value = featureFlagsRepository.flags()
    }

    val flags = featureFlags.value
    if (flags == null) {
      Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
    } else {
      AVALayout(
        versionCode = versionCode,
        versionName = versionName,
        navigationState = navigationState,
        navigator = navigator,
        signinCallbacks = signinCallbacks,
        featureFlags = flags
      )
    }

  }
}

@Composable
expect fun getPlatformContext(): PlatformContext
