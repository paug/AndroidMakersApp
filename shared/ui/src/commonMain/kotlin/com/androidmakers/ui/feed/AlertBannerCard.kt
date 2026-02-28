package com.androidmakers.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.androidmakers.domain.model.FeedItem

@Composable
fun AlertBannerCard(
  alert: FeedItem.Alert,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val shape = RoundedCornerShape(12.dp)
  val containerColor = MaterialTheme.colorScheme.errorContainer
  val contentColor = MaterialTheme.colorScheme.onErrorContainer
  val borderColor = MaterialTheme.colorScheme.error.copy(alpha = 0.3f)

  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(shape)
      .background(containerColor)
      .border(1.dp, borderColor, shape)
      .padding(12.dp),
    verticalAlignment = Alignment.Top,
  ) {
    Icon(
      imageVector = Icons.Rounded.Warning,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.error,
      modifier = Modifier.size(24.dp),
    )
    Spacer(Modifier.width(12.dp))
    Column(
      modifier = Modifier.weight(1f),
    ) {
      Text(
        text = alert.title,
        color = contentColor,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
      )
      Text(
        text = alert.message,
        color = contentColor.copy(alpha = 0.8f),
        fontSize = 13.sp,
        modifier = Modifier.padding(top = 2.dp),
      )
    }
    IconButton(
      onClick = onDismiss,
      modifier = Modifier.size(32.dp),
    ) {
      Icon(
        imageVector = Icons.Rounded.Close,
        contentDescription = "Dismiss",
        tint = contentColor,
        modifier = Modifier.size(18.dp),
      )
    }
  }
}
