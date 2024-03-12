package fr.paug.androidmakers.di

import fr.paug.androidmakers.ui.MainActivityViewModel
import fr.paug.androidmakers.ui.components.AgendaLayoutViewModel
import fr.paug.androidmakers.ui.components.PartnersViewModel
import fr.paug.androidmakers.ui.components.agenda.AgendaPagerViewModel
import fr.paug.androidmakers.ui.components.session.SessionDetailViewModel
import fr.paug.androidmakers.ui.components.speakers.SpeakerViewModel
import fr.paug.androidmakers.ui.components.speakers.details.SpeakerDetailsViewModel
import fr.paug.androidmakers.ui.components.venue.VenueViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
  viewModel { MainActivityViewModel(get(), get(), get(), get(), get(), get(), get()) }
  viewModel { PartnersViewModel(get()) }
  viewModel { AgendaPagerViewModel(get(), get()) }
  viewModel { AgendaLayoutViewModel(get()) }
  viewModel { SpeakerViewModel(get()) }
  viewModel { SpeakerDetailsViewModel(get(), get()) }
  viewModel { SessionDetailViewModel(get(), get(), get(), get(), get())}
  viewModel { VenueViewModel(get(), get()) }
}
