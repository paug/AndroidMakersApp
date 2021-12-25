package fr.androidmakers.store.model

data class Session(
        val complexity: String = "",
        val speakers: List<String> = listOf(),
        val description: String = "",
        val language: String = "",
        val title: String = "",
        val tags: List<String> = listOf(),
        val videoURL: String = "",
        val slido: String? = null,
        val platformUrl: String? = null
)
