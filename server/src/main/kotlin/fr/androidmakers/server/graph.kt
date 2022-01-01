package fr.androidmakers.server

import com.expediagroup.graphql.server.operations.Query
import fr.androidmakers.server.model.Room
import fr.androidmakers.server.model.Session
import fr.androidmakers.server.model.Speaker
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.springframework.stereotype.Component

@Component
class RootQuery : Query {
  fun rooms(): List<Room> {
    return CachedData.rooms
  }
  fun sessions(): List<Session> {
    return CachedData.sessions
  }
  fun speakers(): List<Speaker> {
    return CachedData.speakers
  }

  fun session(id: String): Session {
    return CachedData.sessions.first { it.id == id }
  }
}
