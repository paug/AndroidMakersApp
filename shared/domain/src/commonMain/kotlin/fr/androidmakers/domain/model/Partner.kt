package fr.androidmakers.domain.model

data class PartnerGroup(
    val title: String = "",
    val partners: List<Partner> = arrayListOf()
)
