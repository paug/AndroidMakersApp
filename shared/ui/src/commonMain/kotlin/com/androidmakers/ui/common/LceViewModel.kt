package com.androidmakers.ui.common

import com.androidmakers.ui.model.Lce
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope


abstract class LceViewModel<T> : ViewModel() {
  abstract fun produce(): Flow<Result<T>>

  private val _mutableSharedState = MutableStateFlow<Lce<T>>(Lce.Loading)
  val values = _mutableSharedState.asStateFlow()

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  private var job: Job? = null

  protected fun launch(isRefresh: Boolean) {
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
}
