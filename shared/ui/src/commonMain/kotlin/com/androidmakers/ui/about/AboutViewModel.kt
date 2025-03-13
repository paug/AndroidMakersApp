package com.androidmakers.ui.about

import androidx.lifecycle.ViewModel
import fr.androidmakers.domain.interactor.OpenBlueskyAccountUseCase
import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase

class AboutViewModel(
    val openXAccount: OpenXAccountUseCase,
    val openXHashtag: OpenXHashtagUseCase,
    val openBlueSkyAccount: OpenBlueskyAccountUseCase,
    val openYoutube: OpenYoutubeUseCase,
    val openCoc: OpenCocUseCase,
    val openFaq: OpenFaqUseCase
): ViewModel()
