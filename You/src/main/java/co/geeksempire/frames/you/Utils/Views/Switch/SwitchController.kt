/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/24/23, 8:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.Switch

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Display.dpToInteger

interface SwitchInterface {
    fun switchedOn()
    fun switchedOff()
    fun switchedIt()
}

class SwitchController (private val context: Context, private val switchBackground: AppCompatImageView, private val switchHandheld: AppCompatImageView) {

    companion object {
        const val switchOn = true
        const val switchOff = false
    }

    init {



    }

    var switchStatus = false

    fun switchIt(switchInterface: SwitchInterface) {

        when (switchStatus) {
            switchOn -> {

                switchStatus = true

                switchOn()

            }
            switchOff -> {

                switchStatus = false

                switchOff()

            }
            else -> {

                switchStatus = true

                switchOn()

            }
        }

        switchHandheld.setOnClickListener {

            when (switchStatus) {
                switchOn -> {

                    switchStatus = false

                    switchOff()

                    switchInterface.switchedOff()

                }
                switchOff -> {

                    switchStatus = true

                    switchOn()

                    switchInterface.switchedOn()

                }
                else -> {

                    switchStatus = true

                    switchOn()

                    switchInterface.switchedOn()

                }
            }

            switchInterface.switchedIt()

        }

        switchBackground.setOnClickListener {

            when (switchStatus) {
                switchOn -> {

                    switchStatus = false

                    switchOff()

                    switchInterface.switchedOff()

                }
                switchOff -> {

                    switchStatus = false

                    switchOn()

                    switchInterface.switchedOn()

                }
                else -> {

                    switchStatus = true

                    switchOn()

                    switchInterface.switchedOn()

                }
            }

            switchInterface.switchedIt()

        }

    }

    /**
     * Margin ON -> 11
     **/
    private fun switchOn() {
        Log.d(this@SwitchController.javaClass.simpleName, "Switching On")

        val switchLayoutParameters = switchHandheld.layoutParams as ConstraintLayout.LayoutParams

        val marginValueAnimator = ValueAnimator.ofInt(dpToInteger(context, 63), dpToInteger(context, 11))
        marginValueAnimator.duration = 777
        marginValueAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Int

            switchLayoutParameters.topMargin = animatedValue

            switchHandheld.layoutParams = switchLayoutParameters

        }
        marginValueAnimator.start()

        val colorValueAnimator = ValueAnimator.ofArgb(context.getColor(R.color.premiumDark), context.getColor(R.color.primaryColorGreen))
        colorValueAnimator.duration = 777
        colorValueAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Int

            switchBackground.imageTintList = ColorStateList.valueOf(animatedValue)
            switchHandheld.imageTintList = ColorStateList.valueOf(animatedValue)

        }
        colorValueAnimator.start()

    }

    /**
     * Margin OFF -> 63
     **/
    private fun switchOff() {
        Log.d(this@SwitchController.javaClass.simpleName, "Switching Off")

        val switchLayoutParameters = switchHandheld.layoutParams as ConstraintLayout.LayoutParams

        val marginValueAnimator = ValueAnimator.ofInt(dpToInteger(context, 11), dpToInteger(context, 63))
        marginValueAnimator.duration = 777
        marginValueAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Int

            switchLayoutParameters.topMargin = animatedValue

            switchHandheld.layoutParams = switchLayoutParameters

        }
        marginValueAnimator.start()

        val colorValueAnimator = ValueAnimator.ofArgb(context.getColor(R.color.primaryColorGreen), context.getColor(R.color.premiumDark))
        colorValueAnimator.duration = 1777
        colorValueAnimator.addUpdateListener {
            val animatedValue = it.animatedValue as Int

            switchBackground.imageTintList = ColorStateList.valueOf(animatedValue)
            switchHandheld.imageTintList = ColorStateList.valueOf(animatedValue)

        }
        colorValueAnimator.start()

    }

}