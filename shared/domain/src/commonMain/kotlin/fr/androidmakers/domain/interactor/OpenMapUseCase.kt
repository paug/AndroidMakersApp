package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.PlatformContext

interface OpenMapUseCase {
  operator fun invoke(context: PlatformContext, coordinates: String, name: String)
}
