package fr.paug.androidmakers.util

import android.content.res.ColorStateList
import android.util.Property
import android.view.View

object UIUtils {
  val BACKGROUND_TINT: Property<View, Int> =
      object : Property<View, Int>(Int::class.java, "backgroundTint") {
        override fun set(view: View, color: Int) {
          view.backgroundTintList = ColorStateList.valueOf(color)
        }

        override fun get(view: View): Int {
          return view.backgroundTintList!!.defaultColor
        }
      }
}