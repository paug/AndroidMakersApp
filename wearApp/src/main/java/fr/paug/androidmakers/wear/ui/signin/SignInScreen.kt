package fr.paug.androidmakers.wear.ui.signin

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.horologist.auth.composables.dialogs.SignedInConfirmationDialog
import com.google.android.horologist.auth.composables.screens.AuthErrorScreen
import com.google.android.horologist.auth.ui.googlesignin.signin.GoogleSignInScreen
import com.google.android.horologist.auth.ui.googlesignin.signin.GoogleSignInViewModel
import com.google.android.horologist.compose.layout.ScreenScaffold
import org.koin.androidx.compose.koinViewModel

private const val TAG = "SignInScreen"

@Composable
fun SignInScreen(
    onDismissOrTimeout: () -> Unit,
) {
  ScreenScaffold {
    GoogleSignInScreen(
        modifier = Modifier.fillMaxSize(),
        onAuthCancelled = {
          Log.d(TAG, "onAuthCancelled")
          onDismissOrTimeout()
        },
        failedContent = {
          AuthErrorScreen()
        },
        viewModel = koinViewModel<GoogleSignInViewModel>()
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
