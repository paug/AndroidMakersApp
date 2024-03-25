package fr.paug.androidmakers.di

import fr.paug.androidmakers.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidViewModelModule = module {
  viewModel { MainActivityViewModel(get(), get()) }
}
