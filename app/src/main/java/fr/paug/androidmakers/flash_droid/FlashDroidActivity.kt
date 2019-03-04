package fr.paug.androidmakers.flash_droid

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.ArCoreApk
import com.google.firebase.auth.FirebaseAuth
import fr.paug.androidmakers.R

class FlashDroidActivity : AppCompatActivity() {
    val FLASH_DROID_PREFERENCES = "FLASH_DROID_PREFERENCES"
    val KEY_FIRST_LAUNCH = "KEY_FIRST_LAUNCH"
    val FRAGMENT_TAG = "FRAGMENT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = getSharedPreferences(FLASH_DROID_PREFERENCES, Context.MODE_PRIVATE)

        val availability = ArCoreApk.getInstance().checkAvailability(this)

        val frameLayout = FrameLayout(this)
        frameLayout.id = R.id.flash_droid_framelayout
        setContentView(frameLayout)

        val fragment = if (!availability.isSupported || Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            NougatNeededFragment()
        } else if (FirebaseAuth.getInstance().currentUser == null) {
            val fragment = LoginFragment()
            fragment.setOnLoggedInCallback {
                supportFragmentManager.beginTransaction()
                        .replace(frameLayout.id, HuntFragment(), FRAGMENT_TAG )
                        .commit()
            }
            fragment
        } else {
            HuntFragment()
        }

        supportFragmentManager.beginTransaction()
                .replace(frameLayout.id, fragment, FRAGMENT_TAG )
                .commit()
    }

    @SuppressLint("NewApi")
    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        // all my love of fragments in a single function...
        if (currentFragment != null && currentFragment.isVisible && currentFragment is HuntFragment) {
            if (currentFragment.backPressed()) {
                return
            }
        }
        return super.onBackPressed()
    }
}