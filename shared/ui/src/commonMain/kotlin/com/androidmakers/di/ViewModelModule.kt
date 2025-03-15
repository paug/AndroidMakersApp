package com.androidmakers.di

import com.androidmakers.ui.about.AboutViewModel
import com.androidmakers.ui.agenda.AgendaViewModel
import com.androidmakers.ui.agenda.SessionDetailViewModel
import com.androidmakers.ui.speakers.SpeakerDetailsViewModel
import com.androidmakers.ui.speakers.SpeakerListViewModel
import com.androidmakers.ui.sponsors.SponsorsViewModel
import com.androidmakers.ui.venue.VenueViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
  viewModelOf(::SpeakerListViewModel)
  viewModelOf(::SponsorsViewModel)
  viewModelOf(::VenueViewModel)
  viewModel { (speakerId: String) -> SpeakerDetailsViewModel(speakerId, get(), get()) }
  viewModelOf(::AgendaViewModel)
  viewModel { (sessionId: String) -> SessionDetailViewModel(sessionId, get(), get(), get(), get(), get(), get(), get(), get()) }
  viewModelOf(::AboutViewModel)
}
