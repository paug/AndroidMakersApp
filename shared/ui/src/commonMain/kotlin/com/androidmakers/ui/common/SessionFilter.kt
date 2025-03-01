package com.androidmakers.ui.common

import com.androidmakers.ui.model.UISession

sealed interface SessionFilter {

  fun matches(session: UISession): Boolean

  data object Bookmark : SessionFilter {
    override fun matches(session: UISession) = session.isFavorite
  }

  data class Language(val value: String): SessionFilter {
    override fun matches(session: UISession) = session.language == value

    companion object {
      const val FRENCH = "French"
      const val ENGLISH = "English"
    }
  }

  data class Room(val id: String): SessionFilter {
    override fun matches(session: UISession): Boolean = session.roomId == id
  }
}
