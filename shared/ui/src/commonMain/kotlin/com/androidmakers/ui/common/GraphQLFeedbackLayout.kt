package com.androidmakers.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.ReviewRating
import fr.androidmakers.domain.repo.ReviewRepository
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun GraphQLFeedbackLayout(
  feedbackId: String,
  reviewRepository: ReviewRepository = koinInject(),
) {
  var selectedRating by remember { mutableStateOf<ReviewRating?>(null) }
  var comment by remember { mutableStateOf("") }
  var isSending by remember { mutableStateOf(false) }
  var submitted by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  val scope = rememberCoroutineScope()

  // Load existing review
  LaunchedEffect(feedbackId) {
    reviewRepository.getReview(feedbackId)
      .onSuccess { review ->
        if (review != null) {
          selectedRating = review.rating
          comment = review.comment
        }
      }
  }

  Surface(
    shape = MaterialTheme.shapes.large,
    border = BorderStroke(1.dp, separatorColor()),
    modifier = Modifier.fillMaxWidth().neoBrutalElevation()
  ) {
    Column(
      modifier = Modifier.padding(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = "How was this talk?",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )

      Spacer(modifier = Modifier.height(16.dp))

      // Sentiment buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
      ) {
        SentimentButton(
          emoji = "\uD83D\uDE1E",
          label = "Disappointed",
          isSelected = selectedRating == ReviewRating.Disappointed,
          onClick = { selectedRating = ReviewRating.Disappointed; submitted = false },
        )
        SentimentButton(
          emoji = "\uD83D\uDE10",
          label = "Neutral",
          isSelected = selectedRating == ReviewRating.Neutral,
          onClick = { selectedRating = ReviewRating.Neutral; submitted = false },
        )
        SentimentButton(
          emoji = "\uD83D\uDE0A",
          label = "Happy",
          isSelected = selectedRating == ReviewRating.Happy,
          onClick = { selectedRating = ReviewRating.Happy; submitted = false },
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Comment input
      OutlinedTextField(
        value = comment,
        onValueChange = { comment = it; submitted = false },
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Add a comment (optional)") },
        minLines = 3,
        maxLines = 5,
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Submit button
      Button(
        onClick = {
          val rating = selectedRating ?: return@Button
          isSending = true
          errorMessage = null
          scope.launch {
            reviewRepository.upsertReview(feedbackId, rating, comment)
              .onSuccess { submitted = true }
              .onFailure { errorMessage = it.message ?: "Something went wrong." }
            isSending = false
          }
        },
        enabled = selectedRating != null && !isSending,
        modifier = Modifier.neoBrutalElevation(shadowOffset = 2.dp),
      ) {
        Text(if (isSending) "Sending..." else "Submit feedback")
      }

      if (submitted) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Thank you for your feedback!",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.primary,
        )
      }

      errorMessage?.let {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = it,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.error,
        )
      }
    }
  }
}

@Composable
private fun SentimentButton(
  emoji: String,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit,
) {
  val backgroundColor by animateColorAsState(
    if (isSelected) MaterialTheme.colorScheme.primaryContainer
    else MaterialTheme.colorScheme.surfaceContainerHigh
  )
  val borderColor by animateColorAsState(
    if (isSelected) MaterialTheme.colorScheme.primary
    else MaterialTheme.colorScheme.outlineVariant
  )

  Surface(
    onClick = onClick,
    shape = MaterialTheme.shapes.large,
    color = backgroundColor,
    border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
    modifier = Modifier.neoBrutalElevation(shadowOffset = if (isSelected) 3.dp else 1.dp),
  ) {
    Column(
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = emoji,
        fontSize = 32.sp,
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
        textAlign = TextAlign.Center,
      )
    }
  }
}
