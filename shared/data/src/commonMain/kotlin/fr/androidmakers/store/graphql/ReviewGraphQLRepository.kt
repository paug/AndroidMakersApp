package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.cache.normalized.FetchPolicy
import com.apollographql.cache.normalized.fetchPolicy
import com.apollographql.cache.normalized.isFromCache
import fr.androidmakers.domain.model.Review
import fr.androidmakers.domain.model.ReviewRating
import fr.androidmakers.domain.repo.ReviewRepository
import fr.androidmakers.store.graphql.type.Rating
import fr.androidmakers.store.graphql.type.ReviewInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReviewGraphQLRepository(private val apolloClient: ApolloClient) : ReviewRepository {

  override suspend fun getReview(id: String): Result<Review?> {
    return try {
      val response = apolloClient.query(GetReviewQuery(id)).execute()
      if (response.exception != null) {
        Result.failure(response.exception!!)
      } else {
        Result.success(response.data?.review?.toDomain()).also {
          if (response.isFromCache) {
            // Update from the network in the background
            GlobalScope.launch {
              apolloClient.query(GetReviewQuery(id)).fetchPolicy(FetchPolicy.NetworkOnly).execute()
            }
          }
        }
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  override suspend fun upsertReview(id: String, rating: ReviewRating, comment: String): Result<Review> {
    return try {
      val response = apolloClient.mutation(
        UpsertReviewMutation(
          ReviewInput(
            id = id,
            rating = rating.toGraphQL(),
            comment = comment,
          )
        )
      ).execute()

      if (response.exception != null) {
        return Result.failure(response.exception!!)
      }

      val data = response.data?.upsertReview
      when {
        data?.onReviewSuccess != null -> {
          val review = data.onReviewSuccess.review
          Result.success(
            Review(
              id = review.id,
              rating = review.rating.toDomain(),
              comment = review.comment.toString(),
            )
          )
        }
        data?.onReviewFailure != null -> {
          Result.failure(Exception(data.onReviewFailure.message))
        }
        else -> Result.failure(Exception("Unknown error"))
      }
    } catch (e: Exception) {
      Result.failure(e)
    }
  }

  private fun GetReviewQuery.Review.toDomain(): Review {
    return Review(
      id = id,
      rating = rating.toDomain(),
      comment = comment.toString(),
    )
  }

  private fun Rating.toDomain(): ReviewRating = when (this) {
    Rating.Disappointed -> ReviewRating.Disappointed
    Rating.Neutral -> ReviewRating.Neutral
    Rating.Happy -> ReviewRating.Happy
    Rating.UNKNOWN__ -> ReviewRating.Neutral
  }

  private fun ReviewRating.toGraphQL(): Rating = when (this) {
    ReviewRating.Disappointed -> Rating.Disappointed
    ReviewRating.Neutral -> Rating.Neutral
    ReviewRating.Happy -> Rating.Happy
  }
}
