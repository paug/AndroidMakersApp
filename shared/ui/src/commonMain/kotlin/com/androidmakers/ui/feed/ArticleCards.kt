package com.androidmakers.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.androidmakers.ui.theme.LocalIsNeobrutalism
import com.androidmakers.ui.theme.neoBrutalBorder
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.FeedItem
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.feed_read_more
import org.jetbrains.compose.resources.stringResource

@Composable
fun CategoryTimeRow(
  category: String,
  timeAgo: String,
  modifier: Modifier = Modifier,
) {
  val color = MaterialTheme.colorScheme.onSurfaceVariant
  Row(modifier = modifier) {
    Text(
      text = category.uppercase(),
      color = color,
      fontSize = 11.sp,
      fontWeight = FontWeight.SemiBold,
      letterSpacing = 0.5.sp,
    )
    if (timeAgo.isNotEmpty()) {
      Text(
        text = " \u2022 ",
        color = color,
        fontSize = 11.sp,
      )
      Text(
        text = timeAgo,
        color = color,
        fontSize = 11.sp,
      )
    }
  }
}

@Composable
fun OverlappingAvatars(
  avatarUrls: List<String>,
  modifier: Modifier = Modifier,
) {
  val circularShape = if (LocalIsNeobrutalism.current) RectangleShape else CircleShape
  Row(modifier = modifier) {
    avatarUrls.forEachIndexed { index, url ->
      AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(28.dp)
          .offset(x = (-8 * index).dp)
          .clip(circularShape),
      )
    }
  }
}

@Composable
fun ArticleCardWithImage(
  article: FeedItem.Article,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth().neoBrutalElevation(),
    shape = MaterialTheme.shapes.extraLarge,
    color = MaterialTheme.colorScheme.surfaceContainerHigh,
  ) {
    Column {
      ArticleHeroImage(
        imageUrl = article.imageUrl,
        categoryBadge = article.categoryBadge,
      )
      Column(modifier = Modifier.padding(16.dp)) {
        CategoryTimeRow(article.category, article.timeAgo)
        Text(
          text = article.title,
          color = MaterialTheme.colorScheme.onSurface,
          fontWeight = FontWeight.Bold,
          fontSize = 18.sp,
          modifier = Modifier.padding(top = 8.dp),
        )
        Text(
          text = article.description,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 14.sp,
          maxLines = 3,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.padding(top = 4.dp),
        )
        ArticleCardFooter(
          avatarUrls = article.avatarUrls,
          readMoreUrl = article.readMoreUrl,
        )
      }
    }
  }
}

@Composable
private fun ArticleHeroImage(
  imageUrl: String?,
  categoryBadge: String?,
) {
  val topClipShape = if (LocalIsNeobrutalism.current) RectangleShape
  else RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
  Box {
    AsyncImage(
      model = imageUrl,
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxWidth()
        .height(180.dp)
        .clip(topClipShape),
    )
    if (categoryBadge != null) {
      Text(
        text = categoryBadge,
        color = MaterialTheme.colorScheme.onPrimary,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 0.5.sp,
        modifier = Modifier
          .padding(12.dp)
          .neoBrutalBorder()
          .background(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.85f),
            MaterialTheme.shapes.extraSmall
          )
          .padding(horizontal = 8.dp, vertical = 4.dp),
      )
    }
  }
}

@Composable
private fun ArticleCardFooter(
  avatarUrls: List<String>,
  readMoreUrl: String?,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 12.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    if (avatarUrls.isNotEmpty()) {
      OverlappingAvatars(avatarUrls)
    } else {
      Spacer(Modifier)
    }
    if (readMoreUrl != null) {
      TextButton(onClick = { }) {
        Text(
          text = stringResource(Res.string.feed_read_more),
          color = MaterialTheme.colorScheme.primary,
          fontWeight = FontWeight.SemiBold,
        )
      }
    }
  }
}

@Composable
fun ArticleCardWithLocation(
  article: FeedItem.Article,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth().neoBrutalElevation(),
    shape = MaterialTheme.shapes.large,
    color = MaterialTheme.colorScheme.surfaceContainerHigh,
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      CategoryTimeRow(article.category, article.timeAgo)
      Text(
        text = article.title,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = Modifier.padding(top = 8.dp),
      )
      Text(
        text = article.description,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontSize = 14.sp,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = 4.dp),
      )
      val location = article.location
      if (location != null) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .background(
              MaterialTheme.colorScheme.primaryContainer,
              MaterialTheme.shapes.medium
            )
            .padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Icon(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp),
          )
          Spacer(Modifier.width(8.dp))
          Column {
            Text(
              text = location.name,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              fontWeight = FontWeight.SemiBold,
              fontSize = 14.sp,
            )
            val time = location.time
            if (time != null) {
              Text(
                text = time,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                fontSize = 12.sp,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun ArticleCardWithThumbnail(
  article: FeedItem.Article,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth().neoBrutalElevation(),
    shape = MaterialTheme.shapes.large,
    color = MaterialTheme.colorScheme.surfaceContainerHigh,
  ) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.Top,
    ) {
      Column(modifier = Modifier.weight(1f)) {
        CategoryTimeRow(article.category, article.timeAgo)
        Text(
          text = article.title,
          color = MaterialTheme.colorScheme.onSurface,
          fontWeight = FontWeight.Bold,
          fontSize = 16.sp,
          modifier = Modifier.padding(top = 8.dp),
        )
        Text(
          text = article.description,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          fontSize = 14.sp,
          maxLines = 3,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.padding(top = 4.dp),
        )
      }
      Spacer(Modifier.width(12.dp))
      AsyncImage(
        model = article.thumbnailUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(96.dp)
          .clip(MaterialTheme.shapes.medium),
      )
    }
  }
}
