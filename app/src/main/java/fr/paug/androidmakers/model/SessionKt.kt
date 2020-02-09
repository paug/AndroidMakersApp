package fr.paug.androidmakers.model

data class SessionKt(
        val complexity: String = "",
        val speakers: List<String> = listOf(),
        val description: String = "",
        val language: String = "",
        val title: String = "",
        val tags: List<String> = listOf(),
        val videoURL: String = ""
)
