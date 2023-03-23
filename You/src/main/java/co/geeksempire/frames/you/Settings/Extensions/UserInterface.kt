/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/23/23, 10:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Settings.Extensions

import android.app.ActivityOptions
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.Overly.OverlyFrame
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Settings.Settings
import co.geeksempire.frames.you.Utils.Display.dpToInteger
import co.geeksempire.frames.you.Utils.Display.statusBarHeight
import co.geeksempire.frames.you.Utils.Views.Dialogue.ConfirmDialogue
import co.geeksempire.frames.you.Utils.Views.Dialogue.ConfirmDialogueInterface
import co.geeksempire.frames.you.Utils.Views.Switch.SwitchController
import co.geeksempire.frames.you.Utils.Views.Switch.SwitchInterface

fun Settings.setupUserInterface() {

    /* Start - Accessibility */
    settingLayoutBinding.accessibilityPermission.preferencesTitle.text = getString(R.string.accessibilityTitle)
    settingLayoutBinding.accessibilityPermission.preferencesDescription.text = getString(R.string.accessibilityDescription)

    settingLayoutBinding.accessibilityPermission.preferencesTitle.setOnClickListener {  }

    if (systemSettings.accessibilityServiceEnabled()) {



    } else {

        val switchControllerFloating = SwitchController(applicationContext, settingLayoutBinding.accessibilityPermission.switchBackground, settingLayoutBinding.accessibilityPermission.switchHandheld)
        switchControllerFloating.switchStatus = systemSettings.accessibilityServiceEnabled()
        switchControllerFloating.switchIt(object : SwitchInterface {

            override fun switchedOn() {



            }

            override fun switchedOff() {



            }

            override fun switchedIt() {

                Handler(Looper.getMainLooper()).postDelayed({

                      ConfirmDialogue(this@setupUserInterface, settingLayoutBinding.root)
                          .initialize(getString(R.string.stabilityTitle), getString(R.string.accessibilityDescription))
                          .show(object : ConfirmDialogueInterface {

                              override fun confirmed() {

                                  startActivity(Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                                      ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                              }

                              override fun dismissed() {


                              }

                          })
                    
                }, 999)
            }

        })

    }
    /* End - Accessibility */

    /* Start - Back */
    val backParameters = settingLayoutBinding.back.layoutParams as ConstraintLayout.LayoutParams
    backParameters.topMargin = dpToInteger(applicationContext, 37) + statusBarHeight(applicationContext)
    settingLayoutBinding.back.layoutParams = backParameters

    settingLayoutBinding.back.setOnClickListener {

        this@setupUserInterface.finish()

    }
    /* Start - Back */

    /* Start - Notice */
    if (OverlyFrame.Framing) {

        Handler(Looper.getMainLooper()).postDelayed({

            settingLayoutBinding.noticeRemove.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_up))
            settingLayoutBinding.noticeRemove.root.visibility = View.VISIBLE

        }, 555)

        settingLayoutBinding.noticeRemove.noticeActionBackground.setOnClickListener {

            stopService(Intent(this@setupUserInterface, OverlyFrame::class.java))

            Handler(Looper.getMainLooper()).postDelayed({

                settingLayoutBinding.noticeRemove.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_down))
                settingLayoutBinding.noticeRemove.root.visibility = View.INVISIBLE

            }, 555)

        }

    } else {

        settingLayoutBinding.noticeRemove.root.visibility = View.INVISIBLE

    }
    /* End - Notice */


}