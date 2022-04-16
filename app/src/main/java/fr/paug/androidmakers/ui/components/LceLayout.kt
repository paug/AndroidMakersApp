package fr.paug.androidmakers.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.datatransport.runtime.retries.Retries.retry
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.viewmodel.Lce
import fr.paug.androidmakers.ui.viewmodel.LceViewModel

@Composable
fun LoadingLayout() {
  Box(modifier = Modifier
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

@Composable
fun <T> SwipeRefreshableLceLayout(
    viewModel: LceViewModel<T>,
    content: @Composable (T) -> Unit
) {
  val isRefreshing = viewModel.isRefreshing.collectAsState()
  val lce = viewModel.values.collectAsState()

  SwipeRefresh(
      state = rememberSwipeRefreshState(isRefreshing.value),
      onRefresh = { viewModel.refresh() },
  ) {
    LceLayout(
        lce = lce.value,
    ) {
      content(it)
    }
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
      onRetry = {viewModel.refresh()},
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
  Box(modifier = Modifier
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