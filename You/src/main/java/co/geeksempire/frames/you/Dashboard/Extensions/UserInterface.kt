/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/18/23, 10:49 AM
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
import android.provider.Settings
import android.view.View
import co.geeksempire.frames.you.Dashboard.UI.Dashboard
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Views.Switch.SwitchController
import co.geeksempire.frames.you.Utils.Views.Switch.SwitchInterface

fun Dashboard.setupUserInterface() {

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

                    startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                }, 999)
            }

        })

    }
    /* End - Floating  */

}