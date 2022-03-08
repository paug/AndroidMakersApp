package fr.paug.androidmakers.model

import android.text.TextUtils
import androidx.annotation.StringRes
import fr.paug.androidmakers.R
import fr.paug.androidmakers.model.SocialNetworkHandle
import fr.paug.androidmakers.model.Ribbon
import androidx.annotation.DrawableRes
import fr.paug.androidmakers.model.SocialNetworkHandle.SocialNetworkType

class SocialNetworkHandle(name: String?, link: String?) {
    enum class SocialNetworkType {
        Unknown, Twitter, Facebook, Website, Github;

        @get:DrawableRes
        val socialNetworkIcon: Int
            get() = when (this) {
                Twitter -> R.drawable.ic_network_twitter
                Facebook -> R.drawable.ic_network_facebook
                Github -> R.drawable.ic_network_github
                Website -> R.drawable.ic_network_web
                else -> R.drawable.ic_network_web
            }
    }

    val networkType: SocialNetworkType
    val link: String?

    companion object {
        private fun getSocialNetworkType(networkName: String?): SocialNetworkType {
            if (!TextUtils.isEmpty(networkName)) {
                if (networkName.equals("twitter", ignoreCase = true)) {
                    return SocialNetworkType.Twitter
                } else if (networkName.equals("github", ignoreCase = true)) {
                    return SocialNetworkType.Github
                } else if (networkName.equals("site", ignoreCase = true)) {
                    return SocialNetworkType.Website
                } else if (networkName.equals("facebook", ignoreCase = true)) {
                    return SocialNetworkType.Facebook
                }
            }
            return SocialNetworkType.Unknown
        }
    }

    init {
        networkType = getSocialNetworkType(name)
        this.link = link
    }
}