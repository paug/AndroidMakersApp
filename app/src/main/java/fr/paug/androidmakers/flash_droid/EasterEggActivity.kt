package fr.paug.androidmakers.flash_droid

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import fr.paug.androidmakers.R
import kotlinx.android.synthetic.main.small_ribbon_image.*

class EasterEggActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER

        imageView.setImageResource(R.drawable.oreo)
        setContentView(imageView)
    }
}