package fr.androidmakers.domain.model

class Agenda(
    val sessions: List<Session>,
    val rooms: Map<String, Room>,
    val speakers: Map<String, Speaker>
)
