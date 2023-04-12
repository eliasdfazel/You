/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/12/23, 12:04 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Display

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import co.geeksempire.frames.you.Database.IO.DisplayIO
import co.geeksempire.frames.you.Utils.Operations.nearestNumber
import kotlin.math.pow
import kotlin.math.sqrt

fun columnCount(context: Context, itemWidth: Int) : Int {

    var spanCount = (displayX(context) / dpToPixel(context, itemWidth.toFloat())).toInt()

    if (spanCount < 1) {
        spanCount = 1
    }

    return spanCount
}

fun dpToPixel(context: Context, dp: Float) : Float {

    val resources: Resources = context.resources

    val metrics = resources.displayMetrics

    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun pixelToDp(context: Context, px: Float) : Float {

    return px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.dpToPixel(context: Context) : Float {

    val resources: Resources = context.resources

    val metrics = resources.displayMetrics

    return this@dpToPixel * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.pixelToDp(context: Context) : Float {

    val resources: Resources = context.resources

    val metrics = resources.displayMetrics

    return this@pixelToDp / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun pixelToFloat(context: Context, pixel: Float) : Float {

    return dpToFloat(context, pixelToDp(context, pixel))
}

fun dpToFloat(context: Context, dp: Float) : Float {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics)
}

fun dpToInteger(context: Context, dp: Int) : Int {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
}

fun Float.spToInteger(context: Context) : Float {

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this@spToInteger, context.resources.displayMetrics)
}

fun percentageOfDisplayDiagonal(context: Context, percentageAmount: Float) : Float {

    return ((displayDiagonal(context) * percentageAmount) / 100).toFloat()
}

fun percentageOfDisplayX(context: Context, percentageAmount: Float) : Float {

    return (((displayX(context)) * percentageAmount) / 100).toFloat()
}

/**
 * Get Touch Pixel Position
 * @param displayEnd = displayX() Or displayY()
 **/
fun Float.pixelToPercentage(displayEnd: Float) : Float {

    return (this@pixelToPercentage * 100) / displayEnd
}

/**
 * Get Percentage Pixel Position
 * @param displayEnd = displayX() Or displayY()
 **/
fun Float.percentageToPixel(displayEnd: Float) : Float {

    return (this@percentageToPixel * displayEnd) / 100
}

fun displayX(context: Context) : Int {

    return context.resources.displayMetrics.widthPixels
}

fun displayY(context: Context) : Int {

    return context.resources.displayMetrics.heightPixels
}

fun displayDiagonal(context: Context) : Double {

    return sqrt((displayX(context).toDouble().pow(2.0)) + (displayY(context).toDouble().pow(2.0)))
}

fun statusBarHeight(context: Context) : Int {

    var statusBarHeight = 0

    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        statusBarHeight = context.resources.getDimensionPixelSize(resourceId)
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        statusBarHeight = DisplayIO(context).displayHeight(statusBarHeight)
    }

    return statusBarHeight
}

fun navigationBarHeight(context: Context) : Int {

    var navigationBarHeight = 0

    val resourceIdNavigationBar: Int =
        context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceIdNavigationBar > 0) {
        navigationBarHeight = context.resources.getDimensionPixelSize(resourceIdNavigationBar)
    }

    return navigationBarHeight
}

fun Float.calculatePercentage(percentageAmount: Float) : Float {

    return ((this@calculatePercentage * percentageAmount) / 100).toFloat()
}

fun displaySection(context: Context, X: Int, Y: Int): Int {
    var section = 1
    if (X < displayX(context) / 2 && Y < displayY(context) / 2) { //top-left
        section = 1
    } else if (X > displayX(context) / 2 && Y < displayY(context) / 2) { //top-right
        section = 2
    } else if (X < displayX(context) / 2 && Y > displayY(context) / 2) { //bottom-left
        section = 3
    } else if (X > displayX(context) / 2 && Y > displayY(context) / 2) { //bottom-right
        section = 4
    }
    return section
}

fun displayRatio(context: Context) : String {

    val ratioMap = mapOf (
        "1.777777777777778" to "16:9",
        "2.000000000000000" to "18:9",
        "2.166666666666667" to "19.5:9"
    )

    val ratio: String = (displayY(context).toDouble() / displayX(context).toDouble()).toString()

    var ratioDirectory = ratioMap[ratio]

    if (ratioDirectory == null) {

        ratioDirectory = ratioMap[ratioMap.nearestNumber(ratio.toDouble())]

    }

    Log.d("Display Measurement", "Display Ratio: $ratio")

    Log.d("Display Measurement", "Selected Display Ratio: $ratioDirectory")

    return ratioDirectory!!
}