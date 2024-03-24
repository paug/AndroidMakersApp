package fr.paug.androidmakers.wear.ui.signin

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.horologist.auth.composables.dialogs.SignedInConfirmationDialog
import com.google.android.horologist.auth.composables.screens.AuthErrorScreen
import com.google.android.horologist.auth.ui.googlesignin.signin.GoogleSignInScreen
import com.google.android.horologist.compose.layout.ScreenScaffold
import org.koin.androidx.compose.koinViewModel

private const val TAG = "SignInScreen"

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel(),
    onSignInSuccess: () -> Unit,
    onDismissOrTimeout: () -> Unit,
) {
  ScreenScaffold {
    GoogleSignInScreen(
        modifier = Modifier.fillMaxSize(),
        onAuthCancelled = {
          Log.d(TAG, "onAuthCancelled")
        },
        failedContent = {
          AuthErrorScreen()
        },
        viewModel = GoogleSignInViewModel(
            onSignInSuccess = {
              viewModel.onSignInSuccess()
              onSignInSuccess()
            },
            onSignInFailed = viewModel::onSignInFailed
        )
    ) { successState ->
      SignedInConfirmationDialog(
          modifier = Modifier.fillMaxSize(),
          onDismissOrTimeout = {
            Log.d(TAG, "onDismissOrTimeout")
            onDismissOrTimeout()
          },
          accountUiModel = successState.accountUiModel,
      )
    }
  }
}
