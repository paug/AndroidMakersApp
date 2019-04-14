package fr.paug.androidmakers.model

data class PartnerCollection(
        val order: Int = 0,
        val title: String = "",
        val logos: List<Logo> = arrayListOf()
)

data class Logo(
        val logoUrl: String = "",
        val name: String = "",
        val url: String = ""
)