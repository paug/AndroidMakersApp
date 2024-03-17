package fr.androidmakers.domain.interactor

expect class OpenMapUseCase {
  operator fun invoke(coordinates: String, name: String)
}
