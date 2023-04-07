package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.viewmodel.Lce
import fr.paug.androidmakers.ui.viewmodel.LceViewModel

@Composable
fun LoadingLayout() {
  Box(
      modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(),
      contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@Composable
fun <T> LceLayout(
    lce: Lce<T>,
    onRetry: (() -> Unit)? = null,
    isRefreshing: Boolean = false,
    content: @Composable (T) -> Unit
) {
  when (lce) {
    is Lce.Loading -> LoadingLayout()
    is Lce.Content -> content(lce.content)
    is Lce.Error -> ErrorLayout(
        enabled = !isRefreshing,
        onClick = onRetry
    )
  }
}

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

@Composable
fun <T> ButtonRefreshableLceLayout(
    viewModel: LceViewModel<T>,
    content: @Composable (T) -> Unit
) {
  val isRefreshing = viewModel.isRefreshing.collectAsState()
  val lce = viewModel.values.collectAsState()

  LceLayout(
      lce = lce.value,
      onRetry = { viewModel.refresh() },
      isRefreshing = isRefreshing.value
  ) {
    content(it)
  }
}

@Composable
fun ErrorLayout(
    enabled: Boolean,
    onClick: (() -> Unit)? = null
) {
  Box(
      modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(),
      contentAlignment = Alignment.Center
  ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
          text = stringResource(id = R.string.ohno),
          textAlign = TextAlign.Center
      )
      if (onClick != null) {
        Box(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            enabled = enabled
        ) {
          Text(text = stringResource(id = R.string.retry))
        }
      }
    }
  }
}

@Composable
fun EmptyLayout(modifier: Modifier = Modifier) {
  Box(
      modifier = modifier
          .fillMaxWidth()
          .fillMaxHeight()
          .padding(16.dp),
      contentAlignment = Alignment.Center
  ) {
    Text(
        text = stringResource(id = R.string.empty_events),
        textAlign = TextAlign.Center
    )
  }
}