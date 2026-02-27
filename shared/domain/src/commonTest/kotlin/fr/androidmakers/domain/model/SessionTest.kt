package fr.androidmakers.domain.model

import kotlinx.datetime.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.minutes

class SessionTest {

  private fun session(
    startsAt: LocalDateTime = LocalDateTime(2025, 4, 25, 10, 0),
    endsAt: LocalDateTime = LocalDateTime(2025, 4, 25, 10, 40),
    title: String = "Test Session",
  ) = Session(
    id = "1",
    title = title,
    startsAt = startsAt,
    endsAt = endsAt,
    roomId = "room1",
    isServiceSession = false,
    type = "talk",
  )

  @Test
  fun durationIsComputedFromStartAndEnd() {
    val s = session()
    assertEquals(40.minutes, s.duration)
  }

  @Test
  fun defaultFieldsAreEmpty() {
    val s = session()
    assertTrue(s.speakers.isEmpty())
    assertTrue(s.tags.isEmpty())
    assertEquals("", s.videoURL)
  }
}
