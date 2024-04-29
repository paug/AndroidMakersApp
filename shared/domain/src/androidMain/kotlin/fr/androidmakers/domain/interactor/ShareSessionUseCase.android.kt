package fr.androidmakers.domain.interactor

import androidx.core.app.ShareCompat
import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

actual class ShareSessionUseCase {
  actual operator fun invoke(
    platformContext: PlatformContext,
    session: Session,
    speakers: List<Speaker>,
    formattedDateAndRoom: String
  ) {
    val context = platformContext.context
    val shareText = if (speakers.isEmpty()) {
      "%s: %s (%s)".format(
        // TODO put this in resources
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

    val shareSheetIntent = ShareCompat.IntentBuilder(context)
      .setSubject(session.title)
      .setType("text/plain")
      .setText(shareText)
      .createChooserIntent()

    context.startActivity(shareSheetIntent)
  }
}
