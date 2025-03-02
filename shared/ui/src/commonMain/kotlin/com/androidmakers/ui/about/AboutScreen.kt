package com.androidmakers.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.getPlatformContext
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.about_android_makers
import fr.paug.androidmakers.ui.code_of_conduct
import fr.paug.androidmakers.ui.faq
import fr.paug.androidmakers.ui.ic_network_x
import fr.paug.androidmakers.ui.ic_network_youtube
import fr.paug.androidmakers.ui.logo_android_makers
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
  Column(
      modifier = Modifier
          .fillMaxSize()
          .verticalScroll(state = rememberScrollState())
          .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
  ) {
    val platformContext = getPlatformContext()

    IntroCard(
        onFaqClick = { viewModel.openFaq(platformContext) },
        onCocClick = { viewModel.openCoc(platformContext) }
    )

    SocialCard(
        { viewModel.openXHashtag(platformContext) },
        { viewModel.openXAccount(platformContext) },
        { viewModel.openYoutube(platformContext) }
    )

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(
            Res.string.version,
            versionName,
            versionCode
        ),
    )
  }
}

@Composable
private fun IntroCard(
    onFaqClick: () -> Unit,
    onCocClick: () -> Unit
) {
  val darkMode = isSystemInDarkTheme()
  Column(Modifier.padding(vertical = 8.dp)) {
    Image(
        modifier = Modifier
            .heightIn(max = 128.dp)
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        painter = painterResource(Res.drawable.logo_android_makers),
        contentDescription = "Logo"
    )
    Text(
        modifier = Modifier.padding(16.dp),
        text = stringResource(Res.string.about_android_makers)
    )
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
      ClickableText(
          text = stringResource(Res.string.faq),
          onClick = onFaqClick
      )
      ClickableText(
          text = stringResource(Res.string.code_of_conduct),
          onClick = onCocClick
      )
    }
  }
}

@Composable
private fun SocialCard(
    onXHashtagClick: () -> Unit,
    onXLogoClick: () -> Unit,
    onYouTubeLogoClick: () -> Unit
) {
  val darkMode = isSystemInDarkTheme()
  Card(Modifier.fillMaxWidth()) {
    Column(
      modifier = Modifier.padding(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Row(
          Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
      ) {
        ClickableText(stringResource(Res.string.x_hashtag), onXHashtagClick)
      }
      Row(
          Modifier
              .padding(top = 8.dp),
          horizontalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        SocialIcon(
          onClick = onXLogoClick,
        ) {
          Icon(
            modifier = Modifier.padding(12.dp),
            painter = painterResource(Res.drawable.ic_network_x),
            tint = if (darkMode) {
              Color.White
            } else {
              Color.Black },
            contentDescription = "X"
          )
      }
        SocialIcon(
          onClick = onYouTubeLogoClick
        ) {
          Image(
            modifier = Modifier.padding(8.dp),
            painter = painterResource(Res.drawable.ic_network_youtube),
            contentDescription = "YouTube"
          )
        }
      }
    }
  }
}

@Composable
private fun SocialIcon(
  onClick: () -> Unit,
  content: @Composable () -> Unit,
) {
  Button(
    modifier = Modifier.size(64.dp),
    onClick = onClick,
    shape = CircleShape,
    contentPadding = PaddingValues(4.dp),
    colors = ButtonDefaults.textButtonColors()
  ) {
    content()
  }
}

@Composable
private fun ClickableText(
    text: String,
    onClick: () -> Unit,
) {
  Text(
      modifier = Modifier
          .clickable(onClick = onClick)
          .padding(8.dp),
      text = text,
      style = MaterialTheme.typography.titleSmall,
      color = MaterialTheme.colorScheme.primary
  )
}
