package com.androidmakers.ui.sponsors

import com.androidmakers.ui.common.LceViewModel
import fr.androidmakers.domain.interactor.GetPartnersUseCase
import fr.androidmakers.domain.interactor.OpenPartnerLinkUseCase
import fr.androidmakers.domain.model.PartnerGroup

class SponsorsViewModel(
    getPartnersUseCase: GetPartnersUseCase,
    val openPartnerLink: OpenPartnerLinkUseCase,
) : LceViewModel<List<PartnerGroup>>(
  produce = { getPartnersUseCase() }
)
