package fr.paug.androidmakers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.asitplus.KmmResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

abstract class LceViewModel<T> : ViewModel() {
  abstract fun produce(): Flow<KmmResult<T>>

  private val _mutableSharedState = MutableStateFlow<Lce<T>>(Lce.Loading)
  val values = _mutableSharedState.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private var job: Job? = null

  private fun launch(isRefresh: Boolean) {
    job?.cancel()
    job = viewModelScope.launch {
      produce().map {
        it.fold(
            onSuccess = { Lce.Content(it) },
            onFailure = { Lce.Error }
        )
      }.onStart {
        if (!isRefresh) {
          emit(Lce.Loading)
        }
      }.collect {
        _mutableSharedState.value = it
        if (it != Lce.Loading) {
          _isRefreshing.value = false
        }
      }
    }
  }

  fun refresh() {
    _isRefreshing.value = true
    launch(true)
  }

  init {
    launch(false)
  }
}

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
