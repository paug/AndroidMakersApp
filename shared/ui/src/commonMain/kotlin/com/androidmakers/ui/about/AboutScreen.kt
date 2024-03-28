package com.androidmakers.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import fr.androidmakers.domain.model.Partner
import fr.paug.androidmakers.ui.MR
import moe.tlaster.precompose.koin.koinViewModel
@Composable
fun AboutScreen(
    versionName: String,
    versionCode: String,
) {
  val viewModel = koinViewModel(vmClass = AboutViewModel::class)
  Column(
      modifier = Modifier
          .fillMaxSize()
          .verticalScroll(state = rememberScrollState())
          .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
  ) {
    IntroCard(
        onFaqClick = { viewModel.openFaq() },
        onCocClick = { viewModel.openCoc() }
    )

    SocialCard(
        { viewModel.openXHashtag() },
        { viewModel.openXAccount() },
        { viewModel.openYoutube() }
    )

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(
            MR.strings.version,
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
  Column(Modifier.padding(vertical = 8.dp)) {
    Image(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth(),
        painter = painterResource(MR.images.logo_android_makers),
        contentDescription = "Logo"
    )
    Text(
        modifier = Modifier.padding(16.dp),
        text = stringResource(MR.strings.about_android_makers)
    )
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
      ClickableText(
          text = stringResource(MR.strings.faq),
          onClick = onFaqClick
      )
      ClickableText(
          text = stringResource(MR.strings.code_of_conduct),
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
  Card(Modifier.fillMaxWidth()) {
    Column(Modifier.padding(8.dp)) {
      Row(
          Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
      ) {
        ClickableText(stringResource(MR.strings.x_hashtag), onXHashtagClick)
      }
      Row(
          Modifier
              .fillMaxWidth()
              .padding(top = 8.dp),
          horizontalArrangement = Arrangement.Center
      ) {
        Icon(
            modifier = Modifier
                .size(96.dp, 64.dp)
                .clickable(onClick = onXLogoClick)
                .padding(12.dp),
            painter = painterResource(MR.images.ic_network_x),
            tint = Color(0xFF000000),
            contentDescription = "X"
        )
        Icon(
            modifier = Modifier
                .size(96.dp, 64.dp)
                .clickable(onClick = onYouTubeLogoClick),
            painter = painterResource(MR.images.ic_network_youtube),
            tint = Color(0xffff0000),
            contentDescription = "YouTube"
        )
      }
    }
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
