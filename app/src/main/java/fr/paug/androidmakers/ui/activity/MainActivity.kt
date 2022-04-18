package fr.paug.androidmakers.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.components.AboutActions
import fr.paug.androidmakers.ui.components.MainLayout
import fr.paug.androidmakers.ui.theme.AndroidMakersTheme
import fr.paug.androidmakers.util.CustomTabUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidMakersTheme {
                MainLayout(aboutActions = AboutActions(
                    onFaqClick = ::onFaqClick,
                    onCodeOfConductClick = ::onCodeOfConductClick,
                    onTwitterHashtagClick = ::onTwitterHashtagClick,
                    onTwitterLogoClick = ::onTwitterLogoClick,
                    onYouTubeLogoClick = ::onYouTubeLogoClick,
                    onSponsorClick = ::onSponsorClick
                ))
            }
        }
    }

    private fun onFaqClick() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://androidmakers.fr/faq")))
    }

    private fun onCodeOfConductClick() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://androidmakers.fr/coc")))
    }

    private fun onTwitterHashtagClick() {
        var twitterIntent: Intent
        try {
            // get the Twitter app if possible
            packageManager?.getPackageInfo("com.twitter.android", 0)
            twitterIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("twitter://search?query=%23" + getString(R.string.twitter_hashtag_for_query))
            )
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (e: Exception) {
            // no Twitter app, revert to browser
            twitterIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/search?q=%23" + getString(R.string.twitter_hashtag_for_query))
            )
        }

        startActivity(twitterIntent)
    }

    private fun onTwitterLogoClick() {
        var twitterIntent: Intent
        try {
            // get the Twitter app if possible
            packageManager?.getPackageInfo("com.twitter.android", 0)
            twitterIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("twitter://user?screen_name=" + getString(R.string.twitter_user_name))
            )
            twitterIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        } catch (e: Exception) {
            // no Twitter app, revert to browser
            twitterIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://twitter.com/" + getString(R.string.twitter_user_name))
            )
        }

        startActivity(twitterIntent)
    }

    private fun onYouTubeLogoClick() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_channel))))
    }

    private fun onSponsorClick(url: String) {
        CustomTabUtil.openChromeTab(this, url)
    }

}
