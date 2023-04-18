package fr.paug.androidmakers.ui.components.about

import androidx.annotation.StringRes
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
import fr.androidmakers.store.model.Logo
import fr.androidmakers.store.model.Partner
import fr.paug.androidmakers.BuildConfig
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.viewmodel.Lce


class AboutActions(
    val onFaqClick: () -> Unit = {},
    val onCodeOfConductClick: () -> Unit = {},
    val onTwitterHashtagClick: () -> Unit = {},
    val onTwitterLogoClick: () -> Unit = {},
    val onYouTubeLogoClick: () -> Unit = {},
    val onSponsorClick: (url: String) -> Unit = {},
)

@Composable
fun AboutLayout(
    partnerList: Lce<List<Partner>>,
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
        aboutActions.onTwitterHashtagClick,
        aboutActions.onTwitterLogoClick,
        aboutActions.onYouTubeLogoClick
    )

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        text = stringResource(
            R.string.version,
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
        text = stringResource(R.string.about_android_makers)
    )
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center

    ) {
      ClickableText(R.string.faq, onFaqClick)
      ClickableText(R.string.code_of_conduct, onCocClick)
    }
  }

}

@Composable
private fun SocialCard(
    onTwitterHashtagClick: () -> Unit,
    onTwitterLogoClick: () -> Unit,
    onYouTubeLogoClick: () -> Unit
) {
  Card(Modifier.fillMaxWidth()) {
    Column(Modifier.padding(8.dp)) {
      Row(
          Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
      ) {
        ClickableText(R.string.twitter_hashtag, onTwitterHashtagClick)
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
                .clickable(onClick = onTwitterLogoClick),
            painter = painterResource(R.drawable.ic_network_twitter),
            tint = Color(0xff1d9bf0),
            contentDescription = "Twitter"
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
    @StringRes text: Int,
    onFaqClick: () -> Unit,
) {
  Text(
      modifier = Modifier
          .clickable(onClick = onFaqClick)
          .padding(8.dp),
      text = stringResource(text),
      color = MaterialTheme.colorScheme.primary
  )
}


@Preview
@Composable
private fun AboutLayoutLoadingPreview() {
  AboutLayout(
      partnerList = Lce.Loading,
      aboutActions = AboutActions()
  )
}


@Preview
@Composable
private fun AboutLayoutLoadedPreview() {
  AboutLayout(
      partnerList = Lce.Content(
          listOf(
              Partner(
                  title = "Event Organizers",
                  logos = listOf(
                      Logo(
                          logoUrl = "../images/logos/babbel.jpeg",
                          name = "Babbel",
                          url = "https://babbel.com/"
                      ),
                      Logo(
                          logoUrl = "../images/logos/coyote.png",
                          name = "Coyote",
                          url = "https://corporate.moncoyote.com/"
                      ),
                  )
              ),
              Partner(
                  title = "Gold sponsors",
                  logos = listOf(
                      Logo(
                          logoUrl = "../images/logos/deezer.png",
                          name = "Deezer",
                          url = "https://www.deezer.com/en/company/about"
                      ),
                  )
              ),
          )
      ),
      aboutActions = AboutActions()
  )
}
