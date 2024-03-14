package com.androidmakers.ui.model

import at.asitplus.KmmResult

sealed interface Lce<out T> {
  object Loading : Lce<Nothing>
  class Content<T>(val content: T) : Lce<T>
  object Error : Lce<Nothing>
}

fun <T> KmmResult<T>.toLce(): Lce<T> = if (isSuccess) {
  Lce.Content(getOrThrow())
} else {
  Lce.Error
}
