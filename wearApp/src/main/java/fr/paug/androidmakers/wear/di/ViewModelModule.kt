package fr.paug.androidmakers.wear.di

import fr.paug.androidmakers.wear.ui.main.MainViewModel
import fr.paug.androidmakers.wear.ui.signin.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidViewModelModule = module {
  viewModel { MainViewModel(get(), get(), get(), get(), get()) }
  viewModel { SignInViewModel(get(), get()) }
}
