/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 6:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.Text

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.RadialGradient
import android.graphics.Shader
import android.widget.TextView

fun gradientText(textView: TextView,
                 gradientType: Int = Gradient.HorizontalGradient,
                 gradientColors: IntArray = intArrayOf(Color.RED, Color.MAGENTA),
                 gradientColorsPositions: FloatArray = floatArrayOf(0f, 1f),
                 gradientHorizontalEnd: Float = textView.width.toFloat(),
                 gradientVerticalEnd: Float = textView.height.toFloat(),
                 gradientRadialRadius: Float = textView.width.toFloat()) {

    when (gradientType) {
        Gradient.HorizontalGradient -> {

            textView.apply {

                paint.shader = LinearGradient(
                    0f, 0f,
                    gradientHorizontalEnd, 0f,
                    gradientColors,
                    gradientColorsPositions,
                    Shader.TileMode.CLAMP
                )

            }

        }
        Gradient.VerticalGradient -> {

            textView.apply {

                paint.shader = LinearGradient(
                    0f, 0f,
                    0f, gradientVerticalEnd,
                    gradientColors,
                    gradientColorsPositions,
                    Shader.TileMode.CLAMP
                )

            }

        }
        Gradient.RadialGradient -> {

            textView.apply {

                paint.shader = RadialGradient(
                    0f, 0f,
                    gradientRadialRadius,
                    gradientColors,
                    gradientColorsPositions,
                    Shader.TileMode.CLAMP)

            }

        }
    }

}