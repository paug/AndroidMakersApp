package fr.androidmakers.domain.model

class Agenda(
    val sessions: Map<String, Session>,
    val rooms: Map<String, Room>,
    val speakers: Map<String, Speaker>
)
