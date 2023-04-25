package fr.paug.androidmakers.ui.components.about

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.BuildConfig
import fr.paug.androidmakers.R
@Composable
fun AboutLayout() {
  val context = LocalContext.current

  Column(
      modifier = Modifier
          .fillMaxSize()
          .verticalScroll(state = rememberScrollState())
          .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
  ) {

    IntroCard(
        onFaqClick = {
          context.startActivity(
              Intent(
                  Intent.ACTION_VIEW,
                  Uri.parse("https://androidmakers.droidcon.com/faqs/")
              )
          )
        },
        onCocClick = {
          context.startActivity(
              Intent(
                  Intent.ACTION_VIEW,
                  Uri.parse("https://androidmakers.droidcon.com/code-of-conduct/")
              )
          )
        }
    )

    SocialCard()

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
private fun SocialCard() {
  val context = LocalContext.current
  val twitterHashtagForQuery = stringResource(id = R.string.twitter_hashtag_for_query)
  val twitterUsername = stringResource(id = R.string.twitter_user_name)
  val youtubeChannelUrl = stringResource(id = R.string.youtube_channel)

  Card(Modifier.fillMaxWidth()) {
    Column(Modifier.padding(8.dp)) {
      Row(
          Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center
      ) {
        ClickableText(R.string.twitter_hashtag) {
          var twitterIntent: Intent
          try {
            // get the Twitter app if possible
            context.packageManager?.getPackageInfo("com.twitter.android", 0)
            twitterIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("twitter://search?query=%23$twitterHashtagForQuery")
            )
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
          } catch (e: Exception) {
            // no Twitter app, revert to browser
            twitterIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/search?q=%23$twitterHashtagForQuery")
            )
          }

          context.startActivity(twitterIntent)
        }
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
                .clickable(onClick = {
                  var twitterIntent: Intent
                  try {
                    // Get the Twitter app if possible
                    context.packageManager?.getPackageInfo("com.twitter.android", 0)
                    twitterIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("twitter://user?screen_name=$twitterUsername")
                    )
                    twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                  } catch (e: Exception) {
                    // Twitter app is not installed, revert to browser
                    twitterIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://twitter.com/$twitterUsername")
                    )
                  }

                  context.startActivity(twitterIntent)
                }),
            painter = painterResource(R.drawable.ic_network_twitter),
            tint = Color(0xff1d9bf0),
            contentDescription = "Twitter"
        )
        Icon(
            modifier = Modifier
                .size(96.dp, 64.dp)
                .clickable(onClick = {
                  context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(youtubeChannelUrl)))
                }),
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
  AboutLayout()
}
