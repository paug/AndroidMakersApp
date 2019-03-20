package fr.paug.androidmakers.ui.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import fr.paug.androidmakers.R

class VenueFloorPlanFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val scrollView = ScrollView(context!!)
        val imageView = AppCompatImageView(context)

        val width = context!!.resources.displayMetrics.widthPixels

        val options = BitmapFactory.Options()
        options.inSampleSize = when {
            width < 600 -> 4
            width < 1200 -> 2
            else -> 1
        }
        val bitmap = BitmapFactory.decodeResource(context!!.resources, R.drawable.beffroi1800px, options)

        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        imageView.adjustViewBounds = true
        imageView.setImageBitmap(bitmap)
        scrollView.addView(imageView)
        return scrollView
    }
}