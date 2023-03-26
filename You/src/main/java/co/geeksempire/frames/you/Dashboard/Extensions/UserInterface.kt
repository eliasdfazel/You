/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/26/23, 6:32 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.Dashboard.UI.Dashboard
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Settings.Settings
import co.geeksempire.frames.you.Utils.Animations.AnimationStatus
import co.geeksempire.frames.you.Utils.Animations.multipleColorsRotation
import co.geeksempire.frames.you.Utils.Display.dpToInteger
import co.geeksempire.frames.you.Utils.Display.statusBarHeight
import co.geeksempire.frames.you.Utils.Views.Switch.SwitchController
import co.geeksempire.frames.you.Utils.Views.Switch.SwitchInterface

fun Dashboard.setupUserInterface() {

    multipleColorsRotation(dashboardLayoutBinding.waiting,
        arrayOf(getColor(R.color.primaryColorRed), getColor(R.color.transparent), getColor(R.color.primaryColorGreen)),
        animationDuration = 1357,
        animationStatus = object : AnimationStatus {})

    /* Start - Floating  */
    dashboardLayoutBinding.floatingPermission.preferencesTitle.text = getString(R.string.floatingTitle)
    dashboardLayoutBinding.floatingPermission.preferencesDescription.text = getString(R.string.floatingDescription)

    dashboardLayoutBinding.floatingPermission.preferencesTitle.setOnClickListener {  }

    if (systemSettings.floatingPermissionEnabled()) {

        dashboardLayoutBinding.floatingPermission.root.visibility = View.GONE

    } else {

        val switchControllerFloating = SwitchController(applicationContext,
            dashboardLayoutBinding.floatingPermission.switchBackground, dashboardLayoutBinding.floatingPermission.switchHandheld)
        switchControllerFloating.switchStatus = systemSettings.floatingPermissionEnabled()
        switchControllerFloating.switchIt(object : SwitchInterface {

            override fun switchedOn() {



            }

            override fun switchedOff() {



            }

            override fun switchedIt() {

                Handler(Looper.getMainLooper()).postDelayed({

                    startActivity(Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                }, 999)
            }

        })

    }
    /* End - Floating  */

    /* Start - Preferences */
    val preferencesParameters = dashboardLayoutBinding.preferencesBackground.layoutParams as ConstraintLayout.LayoutParams
    preferencesParameters.topMargin = dpToInteger(applicationContext, 37) + statusBarHeight(applicationContext)
    dashboardLayoutBinding.preferencesBackground.layoutParams = preferencesParameters

    dashboardLayoutBinding.preferences.setOnClickListener {

        startActivity(Intent(this@setupUserInterface, Settings::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle()
        )

    }
    /* End - Preferences */

}