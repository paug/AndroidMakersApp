package com.androidmakers.ui.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.domain.interactor.OpenBlueskyAccountUseCase
import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenGithubRepoUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase
import fr.androidmakers.domain.model.ThemePreference
import fr.androidmakers.domain.repo.ThemeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AboutViewModel(
    val openXAccount: OpenXAccountUseCase,
    val openXHashtag: OpenXHashtagUseCase,
    val openBlueSkyAccount: OpenBlueskyAccountUseCase,
    val openYoutube: OpenYoutubeUseCase,
    val openCoc: OpenCocUseCase,
    val openFaq: OpenFaqUseCase,
    val openGithubRepo: OpenGithubRepoUseCase,
    private val themeRepository: ThemeRepository,
): ViewModel() {

  val themePreference: StateFlow<ThemePreference> = themeRepository.themePreference
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ThemePreference.System)

  fun setThemePreference(preference: ThemePreference) {
    viewModelScope.launch {
      themeRepository.setThemePreference(preference)
    }
  }
}
