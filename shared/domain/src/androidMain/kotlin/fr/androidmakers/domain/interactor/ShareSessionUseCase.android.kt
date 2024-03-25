package fr.androidmakers.domain.interactor

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker

actual class ShareSessionUseCase(private val context: Context) {
  actual operator fun invoke(session: Session, speakers: List<Speaker>, formattedDateAndRoom: String) {
    val speakersString = speakers.joinToString(", ") { it.name ?: "" }

    val shareSessionIntent = Intent(Intent.ACTION_SEND)
    shareSessionIntent.putExtra(Intent.EXTRA_SUBJECT, session.title)
    if (speakers.isEmpty()) {
      shareSessionIntent.putExtra(
        Intent.EXTRA_TEXT,
        String.format(
          "%s: %s (%s)",
          // TODO put this in resources
          "Android Makers",
          session.title,
          formattedDateAndRoom
        )
      )
    } else {
      shareSessionIntent.putExtra(
        Intent.EXTRA_TEXT,
        String.format(
          "%s: %s (%s, %s, %s)",
          "Android Makers",
          session.title,
          speakersString,
          formattedDateAndRoom,
          session.language
        )
      )
    }
    shareSessionIntent.type = "text/plain"
    val shareSheetIntent = Intent.createChooser(shareSessionIntent, null)

    // TODO find a solution to use the activity for starting the intent to remove this flag
    shareSheetIntent.addFlags(FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(shareSheetIntent)
  }
}
