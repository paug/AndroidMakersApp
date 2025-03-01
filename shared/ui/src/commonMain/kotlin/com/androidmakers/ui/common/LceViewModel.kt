package com.androidmakers.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidmakers.ui.model.Lce
import com.androidmakers.ui.model.toLce
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

abstract class LceViewModel<T>(
  produce: (refresh: Boolean) -> Flow<Result<T>>
) : ViewModel() {
  private class LoadingTrigger(val refresh: Boolean)

  private val loadingTrigger = MutableStateFlow(LoadingTrigger(refresh = false))

  @OptIn(ExperimentalCoroutinesApi::class)
  val values: StateFlow<Lce<T>> = loadingTrigger.flatMapLatest { trigger ->
    produce(trigger.refresh)
      .map { it.toLce() }
      .onEach {
        _isRefreshing.value = false
      }
  }.stateIn(
    scope = viewModelScope,
    started = SharingStarted.Eagerly,
    initialValue = Lce.Loading
  )

  private val _isRefreshing = MutableStateFlow(false)
  val isRefreshing = _isRefreshing.asStateFlow()

  fun refresh() {
    _isRefreshing.value = true
    loadingTrigger.value = LoadingTrigger(refresh = true)
  }
}
