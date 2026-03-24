package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.FeatureFlags
import kotlinx.coroutines.flow.Flow


interface FeatureFlagsRepository {
  suspend fun flags(): FeatureFlags
}
