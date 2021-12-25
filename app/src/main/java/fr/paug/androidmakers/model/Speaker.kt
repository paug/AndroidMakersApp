package fr.paug.androidmakers.model

import android.text.TextUtils
import androidx.annotation.StringRes
import fr.paug.androidmakers.R
import fr.paug.androidmakers.model.SocialNetworkHandle
import fr.paug.androidmakers.model.Ribbon
import androidx.annotation.DrawableRes
import fr.paug.androidmakers.model.SocialNetworkHandle.SocialNetworkType

class Speaker(
    val name: String,
    val bio: String,
    val company: String,
    val surname: String,
    val thumbnailUrl: String,
    rockstar: String?,
    socialNetworkHandleList: List<SocialNetworkHandle>,
    ribbonList: List<Ribbon>?
) {
    val ribbonList: List<Ribbon>?
    val rockstar: Boolean
    val socialNetworkHandles: List<SocialNetworkHandle>
    val fullNameAndCompany: String
        get() = name + " " + surname + if (TextUtils.isEmpty(company)) "" else ", " + company
    val fullName: String
        get() = name + " " + surname

    /**
     * Gets the main ribbon.
     * The main ribbon is the first ribbon of the list.
     *
     * @return the main ribbon, or null if the ribbon list is empty or null.
     */
    val mainRibbon: Ribbon?
        get() = if (ribbonList != null && !ribbonList.isEmpty()) {
            ribbonList[0]
        } else null

    init {
        this.rockstar = java.lang.Boolean.parseBoolean(rockstar)
        socialNetworkHandles = socialNetworkHandleList
        this.ribbonList = ribbonList
    }
}