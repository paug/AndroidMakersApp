package fr.androidmakers.domain.repo

import fr.androidmakers.domain.model.Partner
import kotlinx.coroutines.flow.Flow

interface PartnersRepository {

  fun getPartners(): Flow<Result<List<Partner>>>
}
