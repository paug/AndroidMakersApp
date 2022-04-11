package fr.paug.androidmakers.ui.activity

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fr.androidmakers.store.model.Logo
import fr.androidmakers.store.model.Partner
import fr.paug.androidmakers.BuildConfig
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme

@Composable
fun AboutLayout(
    wifiNetwork: String?,
    wifiPassword: String?,
    partnerList: List<Partner>,
    onFaqClick: () -> Unit,
    onCodeOfConductClick: () -> Unit,
    onTwitterHashtagClick: () -> Unit,
    onTwitterLogoClick: () -> Unit,
    onYouTubeLogoClick: () -> Unit,
    onSponsorClick: (url: String) -> Unit,
) {
    AndroidMakersTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.light_grey))
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp)
        ) {
            IntroCard(onFaqClick, onCodeOfConductClick)
            Spacer(Modifier.height(16.dp))
            SocialCard(onTwitterHashtagClick, onTwitterLogoClick, onYouTubeLogoClick)
            if (wifiNetwork != null && wifiPassword != null) {
                Spacer(Modifier.height(16.dp))
                WifiCard(
                    wifiNetwork = wifiNetwork,
                    wifiPassword = wifiPassword
                )
            }
            Spacer(Modifier.height(16.dp))
            SponsorsCard(partnerList = partnerList, onSponsorClick = onSponsorClick)
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.version, BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE),
                color = colorResource(R.color.grey)
            )
        }
    }
}

@Composable
private fun IntroCard(onFaqClick: () -> Unit, onCocClick: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(vertical = 8.dp)) {
            Image(
                modifier = Modifier
                    .height(64.dp)
                    .fillMaxWidth(),
                painter = painterResource(R.drawable.logo_android_makers), contentDescription = "Logo"
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
}

@Composable
private fun SocialCard(onTwitterHashtagClick: () -> Unit, onTwitterLogoClick: () -> Unit, onYouTubeLogoClick: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.social),
                style = MaterialTheme.typography.h6
            )
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
                Image(
                    modifier = Modifier
                        .size(96.dp, 64.dp)
                        .clickable(onClick = onTwitterLogoClick),
                    painter = painterResource(R.drawable.ic_network_twitter),
                    contentDescription = "Twitter"
                )
                Image(
                    modifier = Modifier
                        .size(96.dp, 64.dp)
                        .clickable(onClick = onYouTubeLogoClick),
                    painter = painterResource(R.drawable.ic_network_youtube),
                    contentDescription = "YouTube"
                )
            }
        }
    }
}

@Composable
private fun WifiCard(wifiNetwork: String, wifiPassword: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.wifi),
                style = MaterialTheme.typography.h6
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.wifi_network, wifiNetwork)
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.wifi_password, wifiPassword)
            )
        }
    }
}

@Composable
private fun SponsorsCard(partnerList: List<Partner>, onSponsorClick: (url: String) -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.sponsors),
                style = MaterialTheme.typography.h6
            )

            for (partner in partnerList) {
                // Sponsor "group" (e.g. Organizers, Gold, etc.)
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 8.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = partner.title,
                )

                // Sponsor logo
                for (logo in partner.logos) {
                    val imageUrl = String.format(
                        "https://androidmakers.fr%s",
                        logo.logoUrl.replace("..", "").replace(".svg", ".png")
                    )
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clickable {
                                onSponsorClick(logo.url)
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        model = imageUrl,
                        contentDescription = logo.name
                    )
                }
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
        fontSize = 18.sp,
        color = colorResource(R.color.colorPrimaryDark)
    )
}


@Composable
@Preview
fun AboutLayoutPreview() {
    AboutLayout(
        wifiNetwork = "AndroidMakers",
        wifiPassword = "MayTheForceBeWithYou42",
        partnerList = listOf(
            Partner(
                order = 0,
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
                order = 1,
                title = "Gold sponsors",
                logos = listOf(
                    Logo(
                        logoUrl = "../images/logos/deezer.png",
                        name = "Deezer",
                        url = "https://www.deezer.com/en/company/about"
                    ),
                )
            ),
        ),
        onFaqClick = {},
        onCodeOfConductClick = {},
        onTwitterHashtagClick = {},
        onTwitterLogoClick = {},
        onYouTubeLogoClick = {},
        onSponsorClick = {},
    )
}
