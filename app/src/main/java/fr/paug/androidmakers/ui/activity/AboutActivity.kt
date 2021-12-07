package fr.paug.androidmakers.ui.activity

import android.os.Bundle
import android.widget.FrameLayout
import fr.paug.androidmakers.R
import fr.paug.androidmakers.ui.fragment.AboutFragment

class AboutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frameLayout = FrameLayout(this)
        frameLayout.id = R.id.aboutContainer
        setContentView(frameLayout)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.aboutContainer, AboutFragment())
        }.commit()
    }
}