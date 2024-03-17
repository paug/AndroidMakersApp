package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.androidmakers.ui.common.LceLayout
import com.androidmakers.ui.common.LceViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> SwipeRefreshableLceLayout(
    viewModel: LceViewModel<T>,
    content: @Composable (T) -> Unit
) {
  val isRefreshing = viewModel.isRefreshing.collectAsState()
  val lce = viewModel.values.collectAsState()
  val pullRefreshState = rememberPullRefreshState(isRefreshing.value, { viewModel.refresh() })

  Box(Modifier.pullRefresh(pullRefreshState)) {
    LceLayout(
        lce = lce.value,
    ) {
      content(it)
    }
    PullRefreshIndicator(isRefreshing.value, pullRefreshState, Modifier.align(Alignment.TopCenter))
  }
}
