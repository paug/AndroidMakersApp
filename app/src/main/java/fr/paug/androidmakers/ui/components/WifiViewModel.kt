package fr.paug.androidmakers.ui.components

import fr.androidmakers.store.model.WifiInfo
import fr.paug.androidmakers.AndroidMakersApplication
import fr.paug.androidmakers.ui.viewmodel.LceViewModel
import kotlinx.coroutines.flow.Flow

class WifiViewModel : LceViewModel<WifiInfo?>() {
    override fun produce(): Flow<Result<WifiInfo?>> {
    return AndroidMakersApplication.instance().store.getWifiInfo()
  }
}
