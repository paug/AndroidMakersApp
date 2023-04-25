package fr.paug.androidmakers.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import fr.paug.androidmakers.ui.components.venue.VenuePager
import fr.paug.androidmakers.ui.components.about.AboutLayout
import fr.paug.androidmakers.ui.components.agenda.AgendaLayout
import fr.paug.androidmakers.ui.components.session.SessionDetailLayout
import fr.paug.androidmakers.ui.components.speakers.SpeakerScreen
import fr.paug.androidmakers.ui.components.speakers.details.SpeakerDetailsRoute
import fr.paug.androidmakers.ui.components.sponsors.SponsorsScreen

fun NavGraphBuilder.agendaGraph(navHostController: NavHostController) {
  navigation(
      route = AndroidMakersRoute.AGENDA_ROUTE,
      startDestination = AndroidMakersRoute.AGENDA_LIST
  ) {
    composable(AndroidMakersRoute.AGENDA_LIST) {
      AgendaLayout(navHostController = navHostController)
    }
    composable(AndroidMakersRoute.AGENDA_DETAILS) { navBackStackEntry ->
      val sessionId = navBackStackEntry.arguments?.getString("sessionId")
      val roomId = navBackStackEntry.arguments?.getString("roomId")
      val startTime = navBackStackEntry.arguments?.getString("startTime")
      val endTime = navBackStackEntry.arguments?.getString("endTime")

      if (sessionId != null && roomId != null && startTime != null && endTime != null) {
        SessionDetailLayout(sessionId, roomId, startTime, endTime)
      }
    }
  }
}

fun NavGraphBuilder.venueGraph() {
  navigation(
      route = AndroidMakersRoute.VENUE_ROUTE,
      startDestination = AndroidMakersRoute.VENUE
  ) {
    composable(AndroidMakersRoute.VENUE) {
      VenuePager()
    }
  }
}

fun NavGraphBuilder.speakersGraph(navHostController: NavHostController) {
  navigation(
      route = AndroidMakersRoute.SPEAKERS_ROUTE,
      startDestination = AndroidMakersRoute.SPEAKERS_LIST
  ) {
    composable(AndroidMakersRoute.SPEAKERS_LIST) {
      SpeakerScreen(navHostController = navHostController)
    }
    composable(AndroidMakersRoute.SPEAKER_DETAILS) { navBackStackEntry ->
      navBackStackEntry.arguments?.getString("speakerId")?.let { speakerId ->
        SpeakerDetailsRoute(speakerId = speakerId)
      }
    }
  }
}

fun NavGraphBuilder.sponsorsGraph() {
  navigation(
      route = AndroidMakersRoute.SPONSORS_ROUTE,
      startDestination = AndroidMakersRoute.SPONSORS
  ) {
    composable(AndroidMakersRoute.SPONSORS) {
      SponsorsScreen()
    }
  }
}

fun NavGraphBuilder.aboutGraph() {
  navigation(
      route = AndroidMakersRoute.ABOUT_ROUTE,
      startDestination = AndroidMakersRoute.ABOUT
  ) {
    composable(AndroidMakersRoute.ABOUT) {
      AboutLayout()
    }
  }
}