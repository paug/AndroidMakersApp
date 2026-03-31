package com.androidmakers.ui.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidmakers.ui.common.LceLayout
import fr.androidmakers.domain.model.FeedItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedScreen() {
  val viewModel = koinViewModel<FeedViewModel>()
  val lce by viewModel.values.collectAsStateWithLifecycle()
  val dismissedAlertIds by viewModel.dismissedAlertIds.collectAsStateWithLifecycle()

  LceLayout(lce = lce, onRetry = { viewModel.refresh() }) { feedItems ->
    val visibleItems = feedItems.filter { item ->
      item !is FeedItem.Alert || item.id !in dismissedAlertIds
    }

    LazyColumn(
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      items(
        items = visibleItems,
        key = { it.id }
      ) { item ->
        when (item) {
          is FeedItem.Alert -> {
            AlertBannerCard(
              alert = item,
              onDismiss = { viewModel.dismissAlert(item.id) },
            )
          }

          is FeedItem.Message -> {
            MessageCard(message = item)
          }

          is FeedItem.Article -> {
            when {
              item.imageUrl != null -> ArticleCardWithImage(article = item)
              item.location != null -> ArticleCardWithLocation(article = item)
              item.thumbnailUrl != null -> ArticleCardWithThumbnail(article = item)
              else -> ArticleCardWithLocation(article = item)
            }
          }
        }
      }
    }
  }
}
