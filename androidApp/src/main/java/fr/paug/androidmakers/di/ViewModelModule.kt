package fr.paug.androidmakers.di

import fr.paug.androidmakers.ui.MainActivityViewModel
import fr.paug.androidmakers.ui.components.AgendaLayoutViewModel
import fr.paug.androidmakers.ui.components.agenda.AgendaPagerViewModel
import fr.paug.androidmakers.ui.components.session.SessionDetailViewModel
import fr.paug.androidmakers.ui.components.speakers.details.SpeakerDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidViewModelModule = module {
  viewModel { MainActivityViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
  viewModel { AgendaLayoutViewModel(get()) }
  viewModel { SpeakerDetailsViewModel(get(), get()) }
  viewModel { SessionDetailViewModel(get(), get(), get(), get(), get(), get())}
  factory { AgendaPagerViewModel(get(), get(), get()) }
}
