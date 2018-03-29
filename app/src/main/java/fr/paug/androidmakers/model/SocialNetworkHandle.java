package fr.paug.androidmakers.model;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import fr.paug.androidmakers.R;

/**
 * @author adrien
 * @since 2017.03.22
 */
public class SocialNetworkHandle {

    public enum SocialNetworkType {
        Unknown, Twitter, GooglePlus, Facebook, Website, Github;

        @DrawableRes
        public int getSocialNetworkIcon() {
            switch (this) {
                case Twitter:
                    return R.drawable.ic_network_twitter;
                case GooglePlus:
                    return R.drawable.ic_network_google_plus;
                case Facebook:
                    return R.drawable.ic_network_facebook;
                case Github:
                    return R.drawable.ic_network_github;
                case Website:
                    return R.drawable.ic_network_web;
                default:
                    return R.drawable.ic_network_web;
            }
        }
    }

    public final SocialNetworkType networkType;
    public final String link;

    public SocialNetworkHandle(String name, String link) {
        this.networkType = getSocialNetworkType(name);
        this.link = link;
    }

    private static SocialNetworkType getSocialNetworkType(String networkName) {
        if (!TextUtils.isEmpty(networkName)) {
            if (networkName.equalsIgnoreCase("twitter")) {
                return SocialNetworkType.Twitter;
            } else if (networkName.equalsIgnoreCase("google-plus")) {
                return SocialNetworkType.GooglePlus;
            } else if (networkName.equalsIgnoreCase("github")) {
                return SocialNetworkType.Github;
            } else if (networkName.equalsIgnoreCase("site")) {
                return SocialNetworkType.Website;
            } else if (networkName.equalsIgnoreCase("facebook")) {
                return SocialNetworkType.Facebook;
            }
        }
        return SocialNetworkType.Unknown;
    }
}
