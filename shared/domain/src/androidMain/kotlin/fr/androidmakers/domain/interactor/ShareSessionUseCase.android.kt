package fr.androidmakers.domain.interactor

import android.content.Context
import androidx.core.app.ShareCompat
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

actual class ShareSessionUseCase {
  actual operator fun invoke(
    context: Context,
    session: Session,
    speakers: List<Speaker>,
    formattedDateAndRoom: String
  ) {
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
