package fr.paug.androidmakers.util

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.util.Log

object WifiUtil {
    private const val TAG = "WifiUtil"
    private const val SSID = "\"AndroidMakers\""
    private const val PASSKEY = "\"Makers2018\""

    /**
     * Gets the wifi network id of the venue. If not found, tries to configure it.
     * @param context a context
     * @return the network id if found or created, otherwise -1
     */
    fun getVenuesWifiNetworkId(context: Context): Int {
        val wifiManager = context.applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }
        val configuredNetworks = wifiManager.configuredNetworks
        if (configuredNetworks != null) {
            // look for an existing network with the same SSID to remove it
            // this is because we changed the network passkey after releasing the app.
            for (config in configuredNetworks) {
                if (config != null && SSID == config.SSID) {
                    wifiManager.removeNetwork(config.networkId)
                    Log.i(TAG, "Existing network removed.")
                    // look only for the first network to save time (if another network exists,
                    // it is not a big deal).
                    break
                }
            }
        }

        // if the network could not be found, configure it.
        val conferenceConfig = WifiConfiguration()
        conferenceConfig.SSID = SSID
        conferenceConfig.preSharedKey = PASSKEY
        return wifiManager.addNetwork(conferenceConfig)
    }

    /**
     * Connects to a given network id
     * @param context a context
     * @param networkId the network id to connect to
     * @return true if the connection has been triggered, false otherwise
     */
    fun connectToWifi(context: Context, networkId: Int): Boolean {
        val wifiManager = context.applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        return wifiManager.enableNetwork(networkId, false)
    }

    /**
     * Gets whether or not the currently connected wifi network is the one of the venue.
     *
     * Identification of the network is done on the SSID.
     *
     * @param context a context
     * @return true if the current network has been identified as the one of the venue,
     * false otherwise
     */
    fun isCurrentlyConnectedToVenuesWifi(context: Context): Boolean {
        val wifiManager = context.applicationContext
            .getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInfo = wifiManager.connectionInfo
        return wifiInfo != null && SSID == wifiInfo.ssid
    }
}