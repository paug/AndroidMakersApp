package fr.paug.androidmakers.wear.ui.session

import fr.androidmakers.domain.model.Room
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month

val uiSession1 = UISession(
  session = Session(
    id = "1",
    title = "Android Graphics: the Path to [UI] Riches",
    description = "Android's graphics APIs are extensive and powerful... but maybe a little complicated. This session will show ways to use the graphics APIs to achieve cool effects and improve the visual quality and richness of your applications.",
    roomId = "",
    speakers = emptyList(),
    startsAt = LocalDateTime(2023, Month.APRIL, 27, 9, 15),
    endsAt = LocalDateTime(2023, Month.APRIL, 27, 10, 0),
    isServiceSession = false,
  ),
  speakers = listOf(
    Speaker(
      id = "1",
      name = "Speaker 1",
      bio = "Bio 1",
    ),
    Speaker(
      id = "2",
      name = "Speaker 2",
      bio = "Bio 2",
    )
  ),
  room = Room(
    id = "1",
    name = "Room 1"
  ),
  isBookmarked = true,
)

val uiSession2 = UISession(
  session = Session(
    id = "2",
    title = "Using Compose Runtime to create a client library",
    description = "Jetpack Compose (UI) is a powerful UI toolkit for Android. Have you ever wondered where this power comes from? The answer is Compose Runtime. \r\n\r\nIn this talk, we will see how we can use Compose Runtime to create client libraries. Firstly, we will talk about Compose nodes, Composition, Recomposer, and how they are orchestrated to create a slot table. Then, we will see how the changes in the slot table are applied with an Applier. Moreover, we will touch upon the Snapshot system and how the changes in the state objects trigger a recomposition. Finally, we will create a basic UI toolkit for PowerPoint using Compose Runtime.",
    roomId = "",
    speakers = emptyList(),
    startsAt = LocalDateTime(2023, Month.APRIL, 27, 10, 15),
    endsAt = LocalDateTime(2023, Month.APRIL, 27, 11, 0),
    isServiceSession = false,
  ),
  speakers = listOf(
    Speaker(
      id = "3",
      name = "Speaker 3",
      bio = "Bio 3",
    ),
  ),
  room = Room(
    id = "2",
    name = "Room 2"
  ),
  isBookmarked = false,
)

val uiSessions = listOf(
  uiSession1,
  uiSession2,
)
