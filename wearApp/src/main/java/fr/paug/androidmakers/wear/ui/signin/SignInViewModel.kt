package fr.paug.androidmakers.wear.ui.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.androidmakers.domain.interactor.SyncBookmarksUseCase
import fr.androidmakers.domain.repo.UserRepository
import kotlinx.coroutines.launch

private val TAG = SignInViewModel::class.java.simpleName

class SignInViewModel(
    private val userRepository: UserRepository,
    private val syncBookmarksUseCase: SyncBookmarksUseCase,
) : ViewModel() {
  fun onSignInSuccess() {
    val userId = userRepository.getUser()?.id ?: return
    viewModelScope.launch {
      syncBookmarksUseCase(userId)
    }
  }

  fun onSignInFailed() {
    Log.w(TAG, "onSignInFailed")
  }
}
