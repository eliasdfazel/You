/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/20/23, 6:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.Scrolls

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import androidx.core.widget.NestedScrollView
import co.geeksempire.frames.you.R

class NextedScrollView(context: Context, attributesSet: AttributeSet) : NestedScrollView(context, attributesSet) {

    var typedArray: TypedArray = context.obtainStyledAttributes(attributesSet, R.styleable.NextedScrollView)

    val flingVelocityFraction: Int = typedArray.getInt(R.styleable.NextedScrollView_flingVelocityFraction, 5)

    override fun fling(velocityY: Int) {
        super.fling(velocityY / flingVelocityFraction)

        Log.d(this@NextedScrollView.javaClass.simpleName, "Velocity Fraction: ${flingVelocityFraction}")
    }

}