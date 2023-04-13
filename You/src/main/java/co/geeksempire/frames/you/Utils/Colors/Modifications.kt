/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/13/23, 5:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Colors

import android.graphics.Color
import kotlin.math.roundToInt

fun setColorAlpha(color: Int, alphaValue: Float /* 1 (Opaque) -- 255 (Transparent) */): Int {

    val alpha = (Color.alpha(color) * alphaValue).roundToInt()

    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)

    return Color.argb(alpha, red, green, blue)
}

fun uniqueGradient(allColors: ArrayList<Int>) : IntArray {

    val colorOne: Int = allColors.randomOrNull()?:Color.CYAN

    var colorTwo: Int = allColors.randomOrNull()?:Color.MAGENTA

    if (allColors.isNotEmpty()) {

        if (colorOne == colorTwo) {

            allColors.remove(colorOne)

            colorTwo = allColors.random()

        }

    }

    return intArrayOf(colorOne, colorTwo)
}