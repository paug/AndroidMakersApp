package com.androidmakers.ui.feed

import com.androidmakers.ui.common.LceViewModel
import fr.androidmakers.domain.interactor.GetFeedUseCase
import fr.androidmakers.domain.model.FeedItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FeedViewModel(
  getFeedUseCase: GetFeedUseCase,
) : LceViewModel<List<FeedItem>>(
  produce = { getFeedUseCase() }
) {
  private val _dismissedAlertIds = MutableStateFlow<Set<String>>(emptySet())
  val dismissedAlertIds: StateFlow<Set<String>> = _dismissedAlertIds.asStateFlow()

  fun dismissAlert(alertId: String) {
    _dismissedAlertIds.update { it + alertId }
  }
}
