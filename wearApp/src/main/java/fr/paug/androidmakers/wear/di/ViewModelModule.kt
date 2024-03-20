package fr.paug.androidmakers.wear.di

import fr.paug.androidmakers.wear.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidViewModelModule = module {
  viewModel { MainViewModel(get(), get()) }
}
