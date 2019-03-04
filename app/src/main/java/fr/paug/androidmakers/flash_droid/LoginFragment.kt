package fr.paug.androidmakers.flash_droid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import fr.paug.androidmakers.R
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {
    private val RC_SIGN_IN = 42

    var onLoggedIn: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()

        button.setOnClickListener {
            val providers = arrayListOf(
                    AuthUI.IdpConfig.GoogleBuilder().build()
            )

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
        }
    }
    fun setOnLoggedInCallback(onLoggedIn: () -> Unit) {
        this.onLoggedIn = onLoggedIn
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                onLoggedIn?.invoke()
            } else {
                if (response != null) {
                    Toast.makeText(context, getString(R.string.login_error), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}