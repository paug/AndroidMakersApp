package com.androidmakers.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.model.Lce
import dev.icerock.moko.resources.compose.stringResource
import fr.paug.androidmakers.ui.MR

@Composable
fun LoadingLayout() {
  Box(
      modifier = Modifier.fillMaxSize(),
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

@Composable
fun ErrorLayout(
    enabled: Boolean,
    onClick: (() -> Unit)? = null
) {
  Box(
      modifier = Modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
  ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
          text = stringResource(MR.strings.ohno),
          textAlign = TextAlign.Center
      )
      if (onClick != null) {
        Box(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            enabled = enabled
        ) {
          Text(text = stringResource(MR.strings.retry))
        }
      }
    }
  }
}


@Composable
fun EmptyLayout(modifier: Modifier = Modifier) {
  Box(
      modifier = modifier
          .fillMaxSize()
          .padding(16.dp),
      contentAlignment = Alignment.Center
  ) {
    Text(
        text = stringResource(MR.strings.empty_events),
        textAlign = TextAlign.Center
    )
  }
}

@Composable
fun <T> ButtonRefreshableLceLayout(
    viewModel: LceViewModel<T>,
    content: @Composable (T) -> Unit
) {
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val lce by viewModel.values.collectAsState()

  LceLayout(
      lce = lce,
      onRetry = { viewModel.refresh() },
      isRefreshing = isRefreshing
  ) {
    content(it)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SwipeRefreshableLceLayout(
    viewModel: LceViewModel<T>,
    content: @Composable (T) -> Unit
) {
  val isRefreshing by viewModel.isRefreshing.collectAsState()
  val lce by viewModel.values.collectAsState()
  val pullRefreshState = rememberPullToRefreshState()

  PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = { viewModel.refresh() },
    state = pullRefreshState
  ) {
    LceLayout(
      lce = lce,
    ) {
      content(it)
    }
  }
}
