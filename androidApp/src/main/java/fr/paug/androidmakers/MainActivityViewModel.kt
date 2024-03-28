package fr.paug.androidmakers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import fr.androidmakers.domain.model.User
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(
  private val userRepository: UserRepository,
  val syncBookmarksUseCase: SyncBookmarksUseCase

) : ViewModel() {
  private val _user = MutableStateFlow<User?>(null)
  val user: Flow<User?> = _user

  init {
    viewModelScope.launch {
      _user.emit(userRepository.getUser())

      val currentUser = _user.value
      if (currentUser != null) {
        // fire & forget
        // This is racy but oh well...
        syncBookmarksUseCase(currentUser.id)
      }
    }
  }

  suspend fun setUser(user: User?) {
    _user.emit(user)
    user?.let {
      syncBookmarksUseCase(it.id)
    }
  }
}
