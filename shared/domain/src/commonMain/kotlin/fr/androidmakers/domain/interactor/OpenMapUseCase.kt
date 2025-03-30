package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext

expect class OpenMapUseCase {
  operator fun invoke(context: PlatformContext, coordinates: String, name: String)
}
