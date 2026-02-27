package com.androidmakers.ui.model

sealed interface Lce<out T> {
  data object Loading : Lce<Nothing>
  data class Content<T>(val content: T) : Lce<T>
  data class Error(val exception: Throwable? = null) : Lce<Nothing>
}

fun <T> Result<T>.toLce(): Lce<T> = fold(
  onSuccess = { Lce.Content(it) },
  onFailure = { Lce.Error(it) }
)
