package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext
import fr.androidmakers.domain.model.Session
import fr.androidmakers.domain.model.Speaker
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication

actual class ShareSessionUseCase {
  actual operator fun invoke(
    platformContext: PlatformContext,
    session: Session,
    speakers: List<Speaker>,
    formattedDateAndRoom: String
  ) {
    val speakersString = speakers.joinToString(", ") { it.name ?: "" }

    val sessionInfos = if (speakers.isEmpty()) {
      NSString.stringWithFormat(
        "%@: %@ (%@)",
        ("Android Makers" as NSString),
        (session.title as NSString),
        (formattedDateAndRoom as NSString)
      )
    } else {
      NSString.stringWithFormat(
        "%@: %@ (%@, %@, %@)",
        ("Android Makers" as NSString),
        (session.title as NSString),
      (speakersString as NSString),
      (formattedDateAndRoom as NSString),
      (session.language as NSString)
      )
    }

    val vc = UIActivityViewController(
        activityItems = listOf(sessionInfos),
        applicationActivities = null
    )

    UIApplication.sharedApplication.delegate?.window?.rootViewController?.presentViewController(
        viewControllerToPresent = vc,
        animated = true,
        completion = null
    )
  }
}
