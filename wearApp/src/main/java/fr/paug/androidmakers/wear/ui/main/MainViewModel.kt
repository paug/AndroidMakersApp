package fr.paug.androidmakers.wear.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.domain.interactor.GetAgendaUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val getAgendaUseCase: GetAgendaUseCase,
) : AndroidViewModel(application) {

  fun logAgenda() {
    viewModelScope.launch {
      getAgendaUseCase().collect {
        println(it.getOrNull()?.sessions)
      }
    }
  }
}
