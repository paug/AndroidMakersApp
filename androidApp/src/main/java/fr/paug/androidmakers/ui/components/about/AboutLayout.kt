package fr.paug.androidmakers.ui.components.about

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.androidmakers.domain.model.Partner
import fr.paug.androidmakers.BuildConfig
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.MR
import fr.paug.androidmakers.ui.util.stringResource

class AboutActions(
    val onFaqClick: () -> Unit = {},
    val onCodeOfConductClick: () -> Unit = {},
    val onXHashtagClick: () -> Unit = {},
    val onXLogoClick: () -> Unit = {},
    val onYouTubeLogoClick: () -> Unit = {},
    val onSponsorClick: (partner: Partner) -> Unit = {},
)

@Composable
fun AboutLayout(
    aboutActions: AboutActions,
) {
  Column(
      modifier = Modifier
          .fillMaxSize()
          .verticalScroll(state = rememberScrollState())
          .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
  ) {
    IntroCard(
        onFaqClick = aboutActions.onFaqClick,
        onCocClick = aboutActions.onCodeOfConductClick
    )

    SocialCard(
        aboutActions.onXHashtagClick,
        aboutActions.onXLogoClick,
        aboutActions.onYouTubeLogoClick
    )

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(
            MR.strings.version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
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
        painter = painterResource(R.drawable.logo_android_makers),
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
        ClickableText(stringResource(R.string.x_hashtag), onXHashtagClick)
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
            painter = painterResource(R.drawable.ic_network_x),
            tint = Color(0xFF000000),
            contentDescription = "X"
        )
        Icon(
            modifier = Modifier
                .size(96.dp, 64.dp)
                .clickable(onClick = onYouTubeLogoClick),
            painter = painterResource(R.drawable.ic_network_youtube),
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
      color = MaterialTheme.colorScheme.primary
  )
}


@Preview
@Composable
private fun AboutLayoutLoadingPreview() {
  AboutLayout(
      aboutActions = AboutActions()
  )
}


@Preview
@Composable
private fun AboutLayoutLoadedPreview() {
  AboutLayout(
      aboutActions = AboutActions()
  )
}
