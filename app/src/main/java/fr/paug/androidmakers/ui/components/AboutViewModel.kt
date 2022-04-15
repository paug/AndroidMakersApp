package fr.paug.androidmakers.ui.components

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import fr.paug.androidmakers.AndroidMakersApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AboutViewModel(application: Application) : AndroidViewModel(application) {

  fun getWifiInfo(): WifiInfo? {
    val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    if (!firebaseRemoteConfig.getBoolean("isWifiCardEnabled")) return null
    val wifiNetwork = firebaseRemoteConfig.getString("wifiNetwork").ifEmpty { null }
    val wifiPassword = firebaseRemoteConfig.getString("wifiPassword").ifEmpty { null }
    if (wifiNetwork == null || wifiPassword == null) return null
    return WifiInfo(network = wifiNetwork, password = wifiPassword)
  }

  val partnerList: Flow<PartnerListState> = AndroidMakersApplication.instance().store.getPartners()
      .map {
        PartnerListState.Loaded(it)
      }
}
