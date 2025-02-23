package com.androidmakers.ui.model

sealed interface Lce<out T> {
  data object Loading : Lce<Nothing>
  class Content<T>(val content: T) : Lce<T>
  data object Error : Lce<Nothing>
}

fun <T> Result<T>.toLce(): Lce<T> = fold(
  onSuccess = { Lce.Content(it) },
  onFailure = { Lce.Error }
)
