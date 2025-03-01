package com.androidmakers.di

import com.androidmakers.ui.about.AboutViewModel
import com.androidmakers.ui.agenda.AgendaViewModel
import com.androidmakers.ui.agenda.SessionDetailViewModel
import com.androidmakers.ui.speakers.SpeakerDetailsViewModel
import com.androidmakers.ui.speakers.SpeakerListViewModel
import com.androidmakers.ui.sponsors.SponsorsViewModel
import com.androidmakers.ui.venue.VenueViewModel
import org.koin.dsl.module

val viewModelModule = module {
  factory { SpeakerListViewModel(get()) }
  factory { SponsorsViewModel(get(), get()) }
  factory { VenueViewModel(get(), get(), get()) }
  factory { (speakerId: String) -> SpeakerDetailsViewModel(speakerId, get(), get()) }
  factory { AgendaViewModel(get(), get(), get(), get()) }
  factory { (sessionId: String) -> SessionDetailViewModel(sessionId, get(), get(), get(), get(), get(), get(), get(), get()) }
  factory { AboutViewModel(get(), get(), get(), get(), get()) }
}
