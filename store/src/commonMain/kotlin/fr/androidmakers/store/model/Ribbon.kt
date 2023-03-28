package fr.androidmakers.store.model

import android.text.TextUtils

class Ribbon(ribbonName: String?, title: String?, link: String?) {
  enum class RibbonType {
    NONE, GDE, GDG;
  }

  val ribbonType: RibbonType?
  val title: String?
  val link: String?

  companion object {
    private fun getRibbonType(ribbonName: String?): RibbonType {
      if (!TextUtils.isEmpty(ribbonName)) {
        if (ribbonName.equals("GDE", ignoreCase = true)) {
          return RibbonType.GDE
        } else if (ribbonName.equals("GDG", ignoreCase = true)) {
          return RibbonType.GDG
        }
      }
      return RibbonType.NONE
    }
  }

  init {
    ribbonType = getRibbonType(ribbonName)
    this.title = title
    this.link = link
  }
}