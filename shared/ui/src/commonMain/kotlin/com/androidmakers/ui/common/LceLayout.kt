package com.androidmakers.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.androidmakers.ui.model.Lce
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.empty_events
import fr.paug.androidmakers.ui.ohno
import fr.paug.androidmakers.ui.retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoadingLayout(
  modifier: Modifier = Modifier
) {
  Box(
      modifier = modifier.fillMaxSize(),
      contentAlignment = Alignment.Center
  ) {
    CircularProgressIndicator()
  }
}

@Composable
fun <T> LceLayout(
    lce: Lce<T>,
    isRefreshing: Boolean = false,
    onRetry: (() -> Unit)? = null,
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
          text = stringResource(Res.string.ohno),
          textAlign = TextAlign.Center
      )
      if (onClick != null) {
        Box(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClick,
            enabled = enabled
        ) {
          Text(text = stringResource(Res.string.retry))
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
        text = stringResource(Res.string.empty_events),
        textAlign = TextAlign.Center
    )
  }
}
