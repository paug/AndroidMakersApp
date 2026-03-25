package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.Review
import fr.androidmakers.domain.model.ReviewRating

interface ReviewRepository {
  suspend fun getReview(id: String): Result<Review?>

  suspend fun upsertFeedback(id: String, rating: ReviewRating, comment: String): Result<Review>
}
