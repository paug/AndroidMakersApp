package com.androidmakers.di

import com.androidmakers.ui.about.AboutViewModel
import com.androidmakers.ui.agenda.AgendaLayoutViewModel
import com.androidmakers.ui.agenda.AgendaPagerViewModel
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
  factory { AgendaLayoutViewModel(get()) }
  factory { AgendaPagerViewModel(get(), get(), get(), get()) }
  factory { (sessionId: String) -> SessionDetailViewModel(sessionId, get(), get(), get(), get(), get(), get(), get()) }
  factory { AboutViewModel(get(), get(), get(), get(), get()) }
}
