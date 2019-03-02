package fr.paug.androidmakers.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.TypedValue
import android.view.View
import com.google.android.youtube.player.internal.r

class FloorPlanView(context: Context): View(context) {
    val rectF = RectF()
    val paddingDp = 20f

    override fun onDraw(canvas: Canvas?) {
        val padding = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                paddingDp,
                context.resources.displayMetrics)

        rectF.left = padding
        rectF.top = padding
        rectF.right = width.toFloat() - padding
        rectF.bottom = height.toFloat() - padding

        FloorPlanHelper.drawCanvas1(canvas,rectF, FloorPlanHelper.ResizingBehavior.AspectFit)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = MeasureSpec.getSize(widthMeasureSpec)
        val h = w * 2801 / 1869

        setMeasuredDimension(w, h)
    }
}