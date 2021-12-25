package fr.paug.androidmakers.model

import android.text.TextUtils
import androidx.annotation.DrawableRes
import fr.paug.androidmakers.R

class Ribbon(ribbonName: String?, title: String?, link: String?) {
    enum class RibbonType {
        NONE, GDE, GDG;

        // TODO GOO, WT
        @get:DrawableRes
        val ribbonIcon: Int
            get() = when (this) {
                GDE -> R.drawable.gde_logo
                GDG -> R.drawable.gdg_logo
                else -> R.drawable.transparent
            }

        @get:DrawableRes
        val badgeIcon: Int
            get() = when (this) {
                GDE -> R.drawable.gde_badge
                GDG -> R.drawable.gdg_badge
                else -> R.drawable.transparent
            }
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