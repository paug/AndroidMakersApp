package fr.paug.androidmakers.util;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public final class WifiUtil {

    private static final String TAG = "WifiUtil";

    private static final String SSID = "\"AndroidMakers\"";
    private static final String PASSKEY = "\"Makers2018\"";
    // improvement wifi info : get this from firebase, have this only in one place in code

    /**
     * Private constructor for static utility class.
     */
    private WifiUtil() {
        // no instance
    }

    /**
     * Gets the wifi network id of the venue. If not found, tries to configure it.
     * @param context a context
     * @return the network id if found or created, otherwise -1
     */
    public static int getVenuesWifiNetworkId(@NonNull Context context) {
        final WifiManager wifiManager = (WifiManager)context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        final List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            // look for an existing network with the same SSID to remove it
            // this is because we changed the network passkey after releasing the app.
            for (WifiConfiguration config : configuredNetworks) {
                if (config != null && SSID.equals(config.SSID)) {
                    wifiManager.removeNetwork(config.networkId);
                    Log.i(TAG, "Existing network removed.");
                    // look only for the first network to save time (if another network exists,
                    // it is not a big deal).
                    break;
                }
            }
        }

        // if the network could not be found, configure it.
        final WifiConfiguration conferenceConfig = new WifiConfiguration();
        conferenceConfig.SSID = SSID;
        conferenceConfig.preSharedKey = PASSKEY;

        return wifiManager.addNetwork(conferenceConfig);
    }

    /**
     * Connects to a given network id
     * @param context a context
     * @param networkId the network id to connect to
     * @return true if the connection has been triggered, false otherwise
     */
    public static boolean connectToWifi(@NonNull Context context, int networkId) {
        final WifiManager wifiManager = (WifiManager)context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        return wifiManager.enableNetwork(networkId, false);
    }

    /**
     * Gets whether or not the currently connected wifi network is the one of the venue.
     *
     * Identification of the network is done on the SSID.
     *
     * @param context a context
     * @return true if the current network has been identified as the one of the venue,
     *         false otherwise
     */
    public static boolean isCurrentlyConnectedToVenuesWifi(@NonNull Context context) {
        final WifiManager wifiManager = (WifiManager)context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null && SSID.equals(wifiInfo.getSSID());
    }

}