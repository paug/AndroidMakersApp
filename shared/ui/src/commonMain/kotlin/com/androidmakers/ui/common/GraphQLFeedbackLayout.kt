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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidmakers.ui.theme.neoBrutalElevation
import fr.androidmakers.domain.model.ReviewRating
import fr.androidmakers.domain.repo.ReviewRepository
import fr.androidmakers.domain.repo.UserRepository
import fr.paug.androidmakers.ui.Res
import fr.paug.androidmakers.ui.feedback_comment_label
import fr.paug.androidmakers.ui.feedback_error
import fr.paug.androidmakers.ui.feedback_sending
import fr.paug.androidmakers.ui.feedback_submit
import fr.paug.androidmakers.ui.feedback_thanks
import fr.paug.androidmakers.ui.feedback_title
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun GraphQLFeedbackLayout(
  sessionId: String,
  reviewRepository: ReviewRepository = koinInject(),
  userRepository: UserRepository = koinInject(),
) {
  var selectedRating by remember { mutableStateOf<ReviewRating?>(null) }
  var comment by remember { mutableStateOf("") }
  var isSending by remember { mutableStateOf(false) }
  var submitted by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf<String?>(null) }
  var reviewId by remember { mutableStateOf<String?>(null) }
  val scope = rememberCoroutineScope()
  val fallbackError = stringResource(Res.string.feedback_error)

  LaunchedEffect(sessionId) {
    val userId = userRepository.getInstallationId()
    val id = "$sessionId-$userId"
    reviewId = id
    reviewRepository.getReview(id)
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
    FeedbackContent(
      selectedRating = selectedRating,
      comment = comment,
      isSending = isSending,
      submitted = submitted,
      errorMessage = errorMessage,
      onRatingSelected = { selectedRating = it; submitted = false },
      onCommentChanged = { comment = it; submitted = false },
      onSubmit = {
        val rating = selectedRating ?: return@FeedbackContent
        val id = reviewId ?: return@FeedbackContent
        isSending = true
        errorMessage = null
        scope.launch {
          reviewRepository.upsertFeedback(id, rating, comment)
            .onSuccess { submitted = true }
            .onFailure { errorMessage = fallbackError }
          isSending = false
        }
      },
      submitEnabled = selectedRating != null && reviewId != null && !isSending,
    )
  }
}

@Suppress("LongParameterList")
@Composable
private fun FeedbackContent(
  selectedRating: ReviewRating?,
  comment: String,
  isSending: Boolean,
  submitted: Boolean,
  errorMessage: String?,
  onRatingSelected: (ReviewRating) -> Unit,
  onCommentChanged: (String) -> Unit,
  onSubmit: () -> Unit,
  submitEnabled: Boolean,
) {
  Column(
    modifier = Modifier.padding(20.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = stringResource(Res.string.feedback_title),
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
    )

    Spacer(modifier = Modifier.height(16.dp))

    SentimentRow(selectedRating = selectedRating, onRatingSelected = onRatingSelected)

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      value = comment,
      onValueChange = onCommentChanged,
      modifier = Modifier.fillMaxWidth(),
      label = { Text(stringResource(Res.string.feedback_comment_label)) },
      minLines = 3,
      maxLines = 5,
    )

    Spacer(modifier = Modifier.height(12.dp))

    Button(
      onClick = onSubmit,
      enabled = submitEnabled,
      modifier = Modifier.neoBrutalElevation(shadowOffset = 2.dp),
    ) {
      Text(
        if (isSending) stringResource(Res.string.feedback_sending)
        else stringResource(Res.string.feedback_submit)
      )
    }

    if (submitted) {
      Spacer(modifier = Modifier.height(8.dp))
      Text(
        text = stringResource(Res.string.feedback_thanks),
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

@Composable
private fun SentimentRow(
  selectedRating: ReviewRating?,
  onRatingSelected: (ReviewRating) -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceEvenly,
  ) {
    SentimentButton(
      emoji = "\uD83D\uDE1E",
      isSelected = selectedRating == ReviewRating.Disappointed,
      onClick = { onRatingSelected(ReviewRating.Disappointed) },
    )
    SentimentButton(
      emoji = "\uD83D\uDE10",
      isSelected = selectedRating == ReviewRating.Neutral,
      onClick = { onRatingSelected(ReviewRating.Neutral) },
    )
    SentimentButton(
      emoji = "\uD83D\uDE0A",
      isSelected = selectedRating == ReviewRating.Happy,
      onClick = { onRatingSelected(ReviewRating.Happy) },
    )
  }
}

@Composable
private fun SentimentButton(
  emoji: String,
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
    Text(
      text = emoji,
      fontSize = 32.sp,
      modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    )
  }
}
