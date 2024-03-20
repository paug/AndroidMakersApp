package com.androidmakers.ui.sponsors

import com.androidmakers.ui.common.LceViewModel
import fr.androidmakers.domain.interactor.GetPartnersUseCase
import fr.androidmakers.domain.model.PartnerGroup
import kotlinx.coroutines.flow.Flow

class SponsorsViewModel(
    private val getPartnersUseCase: GetPartnersUseCase
) : LceViewModel<List<PartnerGroup>>() {
  override fun produce(): Flow<Result<List<PartnerGroup>>> {
    return getPartnersUseCase()
  }

  init {
    launch(false)
  }
}
