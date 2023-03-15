/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 6:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Colors

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.palette.graphics.Palette
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Operations.drawableToBitmap

class Palettes(private val context: Context) {

    fun extractVibrantColor(drawable: Drawable?) : Int {

        var vibrantColor = context.getColor(R.color.default_color)

        val bitmap: Bitmap? = when (drawable) {
            is VectorDrawable -> {
                drawableToBitmap(drawable)
            }
            is AdaptiveIconDrawable -> {
                try {
                    (drawable.background as BitmapDrawable).bitmap
                } catch (e: Exception) {
                    try {
                        (drawable.foreground as BitmapDrawable).bitmap
                    } catch (e1: Exception) {
                        drawableToBitmap(drawable)
                    }
                }
            }
            else -> {
                drawable?.let { drawableToBitmap(it) }
            }
        }

        var currentColor: Palette

        try {

            if (bitmap != null && !bitmap.isRecycled) {
                currentColor = Palette.from(bitmap).generate()
                val defaultColor = context.getColor(R.color.default_color)
                vibrantColor = currentColor.getVibrantColor(defaultColor)
            }

        } catch (e: Exception) {
            e.printStackTrace()

            try {
                if (bitmap != null && !bitmap.isRecycled) {
                    currentColor = Palette.from(bitmap).generate()
                    val defaultColor = context.getColor(R.color.default_color)
                    vibrantColor = currentColor.getMutedColor(defaultColor)
                }
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

        }

        return vibrantColor
    }

    fun extractDominantColor(drawable: Drawable?) : Int {

        var dominantColor = context.getColor(R.color.primaryColorPurpleLight)

        val bitmap: Bitmap? = when (drawable) {
            is VectorDrawable -> {
                drawableToBitmap(drawable)
            }
            is AdaptiveIconDrawable -> {
                try {
                    (drawable.background as BitmapDrawable).bitmap
                } catch (e: Exception) {
                    try {
                        (drawable.foreground as BitmapDrawable).bitmap
                    } catch (e1: Exception) {
                        drawableToBitmap(drawable)
                    }
                }
            }
            else -> {
                drawable?.let { drawableToBitmap(it) }
            }
        }

        var currentColor: Palette

        try {

            if (bitmap != null && !bitmap.isRecycled) {
                currentColor = Palette.from(bitmap).generate()
                val defaultColor = context.getColor(R.color.default_color)
                dominantColor = currentColor.getDominantColor(defaultColor)
            }

        } catch (e: Exception) {
            e.printStackTrace()

            try {
                if (bitmap != null && !bitmap.isRecycled) {
                    currentColor = Palette.from(bitmap).generate()
                    val defaultColor = context.getColor(R.color.default_color)
                    dominantColor = currentColor.getMutedColor(defaultColor)
                }
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

        }

        return dominantColor
    }

}