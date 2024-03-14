package fr.androidmakers.di

import fr.androidmakers.domain.utils.UrlOpener
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val domainPlatformModule = module {
  single {
    UrlOpener(androidContext())
  }
}
