/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/12/23, 10:33 AM
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

    val colorOne = allColors.random()

    var colorTwo = allColors.random()

    if (colorOne == colorTwo) {

        allColors.remove(colorOne)

        try {
            colorTwo = allColors.random()
        } catch (e: NoSuchElementException) { }

    }

    return intArrayOf(colorOne, colorTwo)
}