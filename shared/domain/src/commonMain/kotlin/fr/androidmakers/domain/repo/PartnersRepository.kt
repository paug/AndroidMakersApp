package fr.androidmakers.domain.repo

import at.asitplus.KmmResult
import fr.androidmakers.domain.model.PartnerGroup
import kotlinx.coroutines.flow.Flow

interface PartnersRepository {
  fun getPartners(): Flow<KmmResult<List<PartnerGroup>>>
}
