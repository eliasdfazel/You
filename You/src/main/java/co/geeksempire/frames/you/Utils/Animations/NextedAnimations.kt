/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/3/23, 6:38 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Animations

import android.animation.Animator
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.*
import android.view.animation.Animation.AnimationListener
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.hypot

interface AnimationStatus {
    fun animationFinished() {
//        Log.d(this@AnimationStatus.javaClass.simpleName, "Animation Finished")
    }
    fun animationLoopFinished() {
//        Log.d(this@AnimationStatus.javaClass.simpleName, "Animation Loop Finished")
    }
}

fun alphaAnimation(view: View,
                   initialDuration: Long = 999,
                   repeatDelay: Long = 777,
                   repeatCounter: Int = Animation.INFINITE,
                   initialRepeatMode: Int = Animation.REVERSE,
                   animationStatus: AnimationStatus) {

    val alphaAnimation = AlphaAnimation(0.1f, 1.0f).apply {
        duration = initialDuration
        startOffset = repeatDelay
        repeatCount = repeatCounter
        repeatMode = initialRepeatMode
        interpolator = AccelerateDecelerateInterpolator()
    }
    view.startAnimation(alphaAnimation)
    alphaAnimation.start()

    alphaAnimation.setAnimationListener(object : AnimationListener {
        override fun onAnimationStart(animation: Animation?) {

        }

        override fun onAnimationEnd(animation: Animation?) {

            animationStatus.animationFinished()

        }

        override fun onAnimationRepeat(animation: Animation?) {}

    })

}

fun rotateAnimationY(view: View,
                     toY: Float = 180f,
                     initialDuration: Long = 999,
                     repeatDelay: Long = 73,
                     animationStatus: AnimationStatus) {

    val rotateAnimationY = view.animate().apply {
        rotationY(toY)
        duration = initialDuration
        startDelay = repeatDelay
        interpolator = AccelerateDecelerateInterpolator()
    }
    rotateAnimationY.setUpdateListener {



    }
    rotateAnimationY.start()

    Handler(Looper.getMainLooper()).postDelayed({

        animationStatus.animationFinished()

    }, initialDuration)

}



fun multipleColorsRotation(instanceOfView: AppCompatImageView, allColors: Array<Int>, animationDuration: Long = 3333, animationStatus: AnimationStatus) {

    instanceOfView.visibility = View.VISIBLE

    instanceOfView.setImageDrawable(GradientDrawable(GradientDrawable.Orientation.TR_BL, allColors.toIntArray()))

    val rotateAnimation = RotateAnimation(0f, 360f,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f).apply {
        duration = animationDuration
        repeatCount = Animation.INFINITE
        interpolator = OvershootInterpolator()
        repeatMode = Animation.REVERSE
    }
    rotateAnimation.setAnimationListener(object : AnimationListener {

        override fun onAnimationStart(animation: Animation?) {}

        override fun onAnimationEnd(animation: Animation?) {}

        override fun onAnimationRepeat(animation: Animation?) {

            animationStatus.animationLoopFinished()

            if (instanceOfView.visibility == View.GONE
                || instanceOfView.visibility == View.INVISIBLE) {

                rotateAnimation.cancel()

            }

        }

    })

    instanceOfView.startAnimation(rotateAnimation)

}

fun circularHide(viewInstance: View,
                 centerX: Int = 0, centerY: Int = 0,
                 initialDuration: Long = 999) {

    val finalRadius = hypot(centerX.toDouble(), centerY.toDouble()).toFloat()

    val animator: Animator = ViewAnimationUtils.createCircularReveal(
        viewInstance,
        centerX, centerY,
        finalRadius, 0f
    )
    animator.duration = initialDuration
    animator.start()

    Handler(Looper.getMainLooper()).postDelayed({

        viewInstance.visibility = View.INVISIBLE

    }, initialDuration - 13)

}