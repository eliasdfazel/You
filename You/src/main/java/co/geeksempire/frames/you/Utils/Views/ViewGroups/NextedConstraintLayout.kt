/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/18/23, 7:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.ViewGroups

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.Utils.Display.dpToFloat

class NextedConstraintLayout(context: Context, attributesSet: AttributeSet) : ConstraintLayout(context, attributesSet) {

    private lateinit var rectF: RectF

    private val path = Path()

    private var cornerRadius = dpToFloat(context, 19f)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        rectF = RectF(0f, 0f, w.toFloat(), h.toFloat())

        resetPath()
    }

    override fun draw(canvas: Canvas) {

        try {

            canvas?.let {

                val save = canvas.save()

                canvas.clipPath(path)

                super.draw(canvas)

                canvas.restoreToCount(save)

            }

        } catch (e: Exception) { }

    }

    override fun dispatchDraw(canvas: Canvas) {

        try {

            canvas?.let {

                val save = canvas.save()

                canvas.clipPath(path)

                super.dispatchDraw(canvas)

                canvas.restoreToCount(save)

            }

        } catch (e: Exception) { }

    }

    private fun resetPath() {

        path.reset()

        path.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW)

        path.close()

    }

}