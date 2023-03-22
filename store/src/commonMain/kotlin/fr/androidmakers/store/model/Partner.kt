package fr.androidmakers.store.model

data class Partner(
        val title: String = "",
        val logos: List<Logo> = arrayListOf()
)

data class Logo(
        val logoUrl: String = "",
        val name: String = "",
        val url: String = ""
)