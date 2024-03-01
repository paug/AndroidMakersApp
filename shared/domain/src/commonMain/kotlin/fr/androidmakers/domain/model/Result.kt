package fr.androidmakers.domain.model

sealed class AMResult<out S, out F: Throwable> {
  data class Success<out S, out F: Throwable>(val value: S) : AMResult<S, F>()
  data class Failure<out S, out F: Throwable>(val failure: F) : AMResult<S, F>()
}
