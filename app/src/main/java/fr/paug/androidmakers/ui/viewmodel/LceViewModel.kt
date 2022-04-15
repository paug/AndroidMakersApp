package fr.paug.androidmakers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class LceViewModel<T>() : ViewModel() {
  abstract fun produce(): Flow<Result<T>>

  private val _mutableSharedState = MutableStateFlow<Lce<T>>(Lce.Loading)
  val values = _mutableSharedState.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private var job: Job? = null

  private fun launch() {
    job?.cancel()
    job = viewModelScope.launch {
      produce().map {
        it.fold(
            onSuccess = { Lce.Content(it) },
            onFailure = { Lce.Error }
        )
      }.onStart {
        emit(Lce.Loading)
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
    launch()
  }

  init {
    launch()
  }
}

sealed interface Lce<out T> {
  object Loading : Lce<Nothing>
  class Content<T>(val content: T) : Lce<T>
  object Error : Lce<Nothing>
}


