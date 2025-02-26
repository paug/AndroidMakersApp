package com.androidmakers.ui.about

import fr.androidmakers.domain.interactor.OpenCocUseCase
import fr.androidmakers.domain.interactor.OpenFaqUseCase
import fr.androidmakers.domain.interactor.OpenXAccountUseCase
import fr.androidmakers.domain.interactor.OpenXHashtagUseCase
import fr.androidmakers.domain.interactor.OpenYoutubeUseCase
import moe.tlaster.precompose.viewmodel.ViewModel

class AboutViewModel(
    val openXAccount: OpenXAccountUseCase,
    val openXHashtag: OpenXHashtagUseCase,
    val openYoutube: OpenYoutubeUseCase,
    val openCoc: OpenCocUseCase,
    val openFaq: OpenFaqUseCase
): ViewModel()
