package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

class DesktopShareSessionUseCase : ShareSessionUseCase {
  override operator fun invoke(
    context: PlatformContext,
    session: Session,
    speakers: List<Speaker>,
    formattedDateAndRoom: String
  ) {
    val shareText = if (speakers.isEmpty()) {
      "%s: %s (%s)".format(
        "Android Makers",
        session.title,
        formattedDateAndRoom
      )
    } else {
      val speakersString = speakers.joinToString(", ") { it.name.orEmpty() }
      "%s: %s (%s, %s, %s)".format(
        "Android Makers",
        session.title,
        speakersString,
        formattedDateAndRoom,
        session.language
      )
    }

    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    clipboard.setContents(StringSelection(shareText), null)
  }
}
