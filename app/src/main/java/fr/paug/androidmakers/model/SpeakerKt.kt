package fr.paug.androidmakers.model

data class SpeakerKt(
        val badges: List<BadgesItem?>? = null,
        val country: String? = null,
        val photoUrl: String? = null,
        val featured: Boolean? = null,
        val companyLogo: String? = null,
        val name: String? = null,
        val photo: String? = null,
        val bio: String? = null,
        val shortBio: String? = null,
        val company: String? = null,
        val socials: List<SocialsItem?>? = null,
        val order: Int? = null
)