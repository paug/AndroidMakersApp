package fr.androidmakers.store.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.cache.normalized.FetchPolicy
import com.apollographql.cache.normalized.apolloStore
import com.apollographql.cache.normalized.fetchPolicy
import com.apollographql.cache.normalized.isFromCache
import fr.androidmakers.domain.model.Review
import fr.androidmakers.domain.model.ReviewRating
import fr.androidmakers.domain.repo.ReviewRepository
import fr.androidmakers.store.graphql.type.Rating
import fr.androidmakers.store.graphql.type.FeedbackInput
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ReviewGraphQLRepository(private val apolloClient: ApolloClient) : ReviewRepository {

  override suspend fun getReview(id: String): Result<Review?> {
    val response = apolloClient.query(GetFeedbackQuery(id)).execute()
    return if (response.exception != null) {
      Result.failure(response.exception!!)
    } else {
      Result.success(response.data?.feedback?.toDomain()).also {
        if (response.isFromCache) {
          // Update from the network in the background
          GlobalScope.launch {
            apolloClient.query(GetFeedbackQuery(id)).fetchPolicy(FetchPolicy.NetworkOnly).execute()
          }
        }
      }
    }
  }

  override suspend fun upsertFeedback(id: String, rating: ReviewRating, comment: String): Result<Review> {
    val response = apolloClient.mutation(
      UpsertFeedbackMutation(
        FeedbackInput(
          id = id,
          rating = rating.toGraphQL(),
          comment = comment,
        )
      )
    ).execute()

    if (response.exception != null) {
      return Result.failure(response.exception!!)
    }

    val data = response.data?.upsertFeedback
    return when {
      data?.onFeedbackSuccess != null -> {
        val feedback = data.onFeedbackSuccess.feedback
        /**
         * We need to update the cache or else we still have `ROOT_QUERY.foo("$id")` in the cache and that takes
         * precedence over the keyargs.
         *
         * This is so that we can return errors if there are some but here it means we'll have null every time
         *
         * See also https://github.com/apollographql/apollo-kotlin-normalized-cache/blob/9ad6f2ffaaefc08223d02d5bc0fa3bcb5e49a7cf/normalized-cache/src/commonMain/kotlin/com/apollographql/cache/normalized/api/CacheResolver.kt#L325
         */
        apolloClient.apolloStore.writeOperation(
          GetFeedbackQuery(id),
          GetFeedbackQuery.Data(
            feedback = GetFeedbackQuery.Feedback(
              __typename = "Feedback",
              id = feedback.id,
              rating = feedback.rating,
              comment = feedback.comment
            )
          )
        )
        Result.success(
          Review(
            id = feedback.id,
            rating = feedback.rating.toDomain(),
            comment = feedback.comment,
          )
        )
      }

      data?.onFeedbackFailure != null -> {
        Result.failure(Exception(data.onFeedbackFailure.message))
      }

      else -> Result.failure(Exception("Unknown error"))
    }
  }

  private fun GetFeedbackQuery.Feedback.toDomain(): Review {
    return Review(
      id = id,
      rating = rating.toDomain(),
      comment = comment,
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
