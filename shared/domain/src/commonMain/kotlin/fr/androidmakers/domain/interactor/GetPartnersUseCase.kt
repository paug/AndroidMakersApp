package fr.androidmakers.domain.interactor

import fr.androidmakers.domain.repo.PartnersRepository

class GetPartnersUseCase(
    private val partnersRepository: PartnersRepository
) {
  operator fun invoke() = partnersRepository.getPartners()
}
