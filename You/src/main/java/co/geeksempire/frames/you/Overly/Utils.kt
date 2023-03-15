/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 7:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Overly

import android.content.Context
import android.graphics.PixelFormat
import android.util.TypedValue
import android.view.Gravity
import android.view.WindowManager
import co.geeksempire.frames.you.Utils.Display.dpToInteger

fun generateLayoutParameters(context: Context, height: Int, width: Int) : WindowManager.LayoutParams {

    val marginClear = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5f, context.resources.displayMetrics).toInt()

    val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
        dpToInteger(context, width) + marginClear,
        dpToInteger(context, height) + marginClear,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        PixelFormat.TRANSLUCENT
    )
    layoutParams.gravity = Gravity.TOP or Gravity.START
    layoutParams.x = 0
    layoutParams.y = 0
    layoutParams.windowAnimations = android.R.style.Animation_Dialog

    return layoutParams
}