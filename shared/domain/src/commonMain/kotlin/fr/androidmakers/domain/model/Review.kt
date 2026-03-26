package fr.androidmakers.domain.model

enum class ReviewRating {
  Disappointed,
  Neutral,
  Happy,
}

data class Review(
  val id: String,
  val rating: ReviewRating,
  val comment: String,
)
