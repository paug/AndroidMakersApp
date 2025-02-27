package com.androidmakers.ui.common

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
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

abstract class LceViewModel<T>(
  produce: () -> Flow<Result<T>>
) : ViewModel() {
  private val loadingTrigger = MutableStateFlow(0)

  @OptIn(ExperimentalCoroutinesApi::class)
  val values: StateFlow<Lce<T>> = loadingTrigger.flatMapLatest {
    produce()
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
    loadingTrigger.update { it + 1 }
  }
}
