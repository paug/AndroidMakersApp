package fr.paug.androidmakers.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import fr.paug.androidmakers.ui.util.imageUrl
import fr.paug.androidmakers.ui.viewmodel.Lce


data class WifiInfo(val network: String, val password: String)

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
    wifiInfo: WifiInfo?,
    partnerList: Lce<List<Partner>>,
    aboutActions: AboutActions,
) {
    AndroidMakersTheme {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.light_grey))
                .verticalScroll(state = rememberScrollState())
                .padding(16.dp)
        ) {
            IntroCard(aboutActions.onFaqClick, aboutActions.onCodeOfConductClick)
            Spacer(Modifier.height(16.dp))
            SocialCard(aboutActions.onTwitterHashtagClick, aboutActions.onTwitterLogoClick, aboutActions.onYouTubeLogoClick)

            if (wifiInfo != null) {
                Spacer(Modifier.height(16.dp))
                WifiCard(
                    wifiNetwork = wifiInfo.network,
                    wifiPassword = wifiInfo.password
                )
            }
            Spacer(Modifier.height(16.dp))
            SponsorsCard(partnerList = partnerList, onSponsorClick = aboutActions.onSponsorClick)
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
                style = MaterialTheme.typography.h5
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
                style = MaterialTheme.typography.h5
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
private fun SponsorsCard(partnerList: Lce<List<Partner>>, onSponsorClick: (url: String) -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = stringResource(R.string.sponsors),
                style = MaterialTheme.typography.h5
            )

            when (partnerList) {
                is Lce.Loading, Lce.Error -> {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }
                is Lce.Content -> {
                    for (partner in partnerList.content) {
                        // Sponsor "group" (e.g. Organizers, Gold, etc.)
                        Text(
                            modifier = Modifier
                                .padding(top = 32.dp, bottom = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = partner.title,
                        )

                        // Sponsor logo
                        for (logo in partner.logos) {
                            Spacer(modifier = Modifier.height(16.dp))
                            AsyncImage(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .clickable {
                                        onSponsorClick(logo.url)
                                    }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                model = imageUrl(logo.logoUrl),
                                contentDescription = logo.name
                            )
                        }
                    }
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


@Preview
@Composable
private fun AboutLayoutLoadingPreview() {
    AboutLayout(
        wifiInfo = WifiInfo(
            network = "AndroidMakers",
            password = "MayTheForceBeWithYou42",
        ),
        partnerList = Lce.Loading,
        aboutActions = AboutActions()
    )
}


@Preview
@Composable
private fun AboutLayoutLoadedPreview() {
    AboutLayout(
        wifiInfo = WifiInfo(
            network = "AndroidMakers",
            password = "MayTheForceBeWithYou42",
        ),
        partnerList = Lce.Content(
            listOf(
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
            )
        ),
        aboutActions = AboutActions()
    )
}
