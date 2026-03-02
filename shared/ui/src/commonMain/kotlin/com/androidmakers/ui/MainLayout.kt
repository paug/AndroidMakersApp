package com.androidmakers.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.common.navigation.AVALayout
import com.androidmakers.ui.common.navigation.AboutKey
import com.androidmakers.ui.common.navigation.AgendaKey
import com.androidmakers.ui.common.navigation.FeedKey
import com.androidmakers.ui.common.navigation.Navigator
import com.androidmakers.ui.common.navigation.SpeakersKey
import com.androidmakers.ui.common.navigation.SponsorsKey
import com.androidmakers.ui.common.navigation.VenueKey
import com.androidmakers.ui.common.navigation.parseDeepLink
import com.androidmakers.ui.common.navigation.rememberNavigationState
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.utils.FeatureFlags


/**
 * The main layout: entry point of the application
 */
@Composable
fun MainLayout(
    versionCode: String,
    versionName: String,
    signinCallbacks: SigninCallbacks,
    deeplink: String? = null,
) {
  setSingletonImageLoaderFactory { context ->
    ImageLoader.Builder(context)
      .crossfade(true)
      .build()
  }

  val startRoute = if (FeatureFlags.isFeedEnabled) FeedKey else AgendaKey
  val topLevelRoutes = buildSet {
    if (FeatureFlags.isFeedEnabled) add(FeedKey)
    add(AgendaKey)
    if (!FeatureFlags.isFeedEnabled) add(VenueKey)
    add(SpeakersKey)
    add(SponsorsKey)
    add(AboutKey)
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

  AVALayout(
    versionCode = versionCode,
    versionName = versionName,
    navigationState = navigationState,
    navigator = navigator,
    signinCallbacks = signinCallbacks,
  )
}

@Composable
expect fun getPlatformContext(): PlatformContext
