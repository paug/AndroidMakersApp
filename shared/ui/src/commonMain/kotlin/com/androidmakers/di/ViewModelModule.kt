package com.androidmakers.di

import com.androidmakers.ui.speakers.SpeakerDetailsViewModel
import com.androidmakers.ui.speakers.SpeakerListViewModel
import com.androidmakers.ui.sponsors.SponsorsViewModel
import com.androidmakers.ui.venue.VenueViewModel
import org.koin.dsl.module

val viewModelModule = module {
  factory { SpeakerListViewModel(get()) }
  factory { SponsorsViewModel(get()) }
  factory { VenueViewModel(get(), get(), get()) }
  factory { (speakerId: String) -> SpeakerDetailsViewModel(speakerId, get()) }

}
