/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/18/23, 8:46 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.Extensions

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import co.geeksempire.frames.you.BuildConfig
import co.geeksempire.frames.you.Dashboard.UI.Dashboard
import co.geeksempire.frames.you.Database.PackageInformation.PackageInformation
import co.geeksempire.frames.you.R

fun Dashboard.sharingProcess() {

    val packageInformation = PackageInformation(applicationContext)

    Handler(Looper.getMainLooper()).postDelayed({

        if (packageInformation.readCurrentVersion() < BuildConfig.VERSION_CODE) {

            packageInformation.saveCurrentVersion()

            dashboardLayoutBinding.sharingView.root.apply {
                visibility = View.VISIBLE
                startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
            }

            Handler(Looper.getMainLooper()).postDelayed({

                dashboardLayoutBinding.sharingView.contentWrapper.apply {
                    visibility = View.VISIBLE
                    startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_up))
                }

            }, 333)

            dashboardLayoutBinding.sharingView.confirmButton.setOnClickListener {

                val sharingIntent = Intent(Intent.ACTION_SEND).apply {
                    this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    this.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharingText))
                    this.type = "text/plain"
                }
                startActivity(sharingIntent)

                dashboardLayoutBinding.sharingView.dismissButton.performClick()

                pointsIO.storePoints()

            }

            dashboardLayoutBinding.sharingView.dismissButton.setOnClickListener {

                dashboardLayoutBinding.sharingView.contentWrapper.apply {
                    startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_down))
                    visibility = View.VISIBLE
                }

                Handler(Looper.getMainLooper()).postDelayed({

                    dashboardLayoutBinding.sharingView.root.apply {
                        startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                        visibility = View.GONE
                    }

                }, 333)

            }

        }

    }, 777)

}