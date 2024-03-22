package fr.paug.androidmakers.di

import fr.paug.androidmakers.ui.MainActivityViewModel
import com.androidmakers.ui.agenda.AgendaLayoutViewModel
import com.androidmakers.ui.agenda.AgendaPagerViewModel
import com.androidmakers.ui.agenda.SessionDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidViewModelModule = module {
  viewModel { MainActivityViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

  factory { (sessionId: String) -> SessionDetailViewModel(sessionId, get(), get(), get(), get(), get()) }

}
