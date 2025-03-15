package fr.paug.androidmakers.wear.di

import com.google.android.horologist.auth.ui.googlesignin.signin.GoogleSignInViewModel
import fr.paug.androidmakers.wear.ui.main.MainViewModel
import fr.paug.androidmakers.wear.ui.session.details.SessionDetailViewModel
import fr.paug.androidmakers.wear.ui.settings.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
  viewModelOf(::MainViewModel)
  viewModelOf(::SettingsViewModel)
  viewModelOf(::SessionDetailViewModel)
  viewModelOf(::GoogleSignInViewModel)
}
