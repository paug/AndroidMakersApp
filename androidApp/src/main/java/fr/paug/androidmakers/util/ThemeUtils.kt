package fr.paug.androidmakers.util

import android.content.Context
import android.util.TypedValue
import fr.paug.androidmakers.R

object ThemeUtils {
  private val TYPED_VALUE: ThreadLocal<TypedValue> = object : ThreadLocal<TypedValue>() {
    override fun initialValue(): TypedValue {
      return TypedValue()
    }
  }

  fun ensureRuntimeTheme(context: Context) {
    val tv = TYPED_VALUE.get()
    context.theme.resolveAttribute(R.attr.runtimeTheme, tv, true)
    require(tv.resourceId > 0) { "runtimeTheme not defined in the preview theme" }
    context.setTheme(tv.resourceId)
  }
}