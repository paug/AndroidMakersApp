package fr.paug.androidmakers.desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.androidmakers.di.viewModelModule
import com.androidmakers.ui.MainLayout
import com.androidmakers.ui.common.SigninCallbacks
import com.androidmakers.ui.theme.AndroidMakersTheme
import fr.androidmakers.di.DependenciesBuilder

fun main() {
  DependenciesBuilder().inject(listOf(viewModelModule))

  application {
    Window(
      onCloseRequest = ::exitApplication,
      title = "Android Makers"
    ) {
      AndroidMakersTheme {
        MainLayout(
          versionCode = "1",
          versionName = "1.0.0-desktop",
          signinCallbacks = SigninCallbacks(
            signin = { /* No-op on Desktop */ },
            signout = { /* No-op on Desktop */ }
          )
        )
      }
    }
  }
}
