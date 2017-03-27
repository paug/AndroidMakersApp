package fr.paug.androidmakers.util;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

import fr.paug.androidmakers.R;

/**
 * Created by benjamin on 27/03/2017.
 */
public class CustomTabUtil {

    public static void openChromeTab(Context context, String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

}