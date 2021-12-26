package fr.androidmakers.store.model

import android.text.TextUtils

data class Speaker(
        val id: String = "",
        val badges: List<BadgesItem?>? = null,
        val country: String? = null,
        val featured: Boolean? = null,
        val companyLogo: String? = null,
        val name: String? = null,
        val photo: String? = null,
        val bio: String? = null,
        val shortBio: String? = null,
        val company: String? = null,
        val socials: List<SocialsItem?>? = null,
        val order: Int? = null
) {

    fun getFullNameAndCompany(): String {
        return this.name + if (TextUtils.isEmpty(company)) "" else ", " + this.company
    }

    fun getMainRibbon(): BadgesItem? {
        return if (badges != null && !badges.isEmpty()) {
            badges.get(0)
        } else null
    }

}