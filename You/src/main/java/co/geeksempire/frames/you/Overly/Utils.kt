/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/12/23, 11:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Overly

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.Dashboard.UI.Frames.Preview.FramePreview
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Display.displayX
import co.geeksempire.frames.you.Utils.Display.displayY
import co.geeksempire.frames.you.Utils.Display.navigationBarHeight
import co.geeksempire.frames.you.Utils.Display.statusBarHeight
import co.geeksempire.frames.you.Utils.Notifications.NotificationsCreator
import co.geeksempire.frames.you.Utils.Views.Dialogue.ConfirmDialogue
import co.geeksempire.frames.you.Utils.Views.Dialogue.ConfirmDialogueInterface

/**
 * @param  yOffset: Status Bar Height
 **/
fun generateLayoutParameters(context: Context) : WindowManager.LayoutParams {

    val frameHeight = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q) {

        displayY(context) + navigationBarHeight(context)

    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

        displayY(context)

    } else {

        displayY(context) + navigationBarHeight(context) + statusBarHeight(context)

    }

    val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
        /* Width */ displayX(context),
        /* Height */ frameHeight,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        PixelFormat.TRANSLUCENT
    )
    layoutParams.gravity = Gravity.TOP or Gravity.START
    layoutParams.x = 0
    layoutParams.y = -(statusBarHeight(context))
    layoutParams.windowAnimations = android.R.style.Animation_Dialog

    return layoutParams
}

fun generateLayoutParametersHorizontal(context: Context) : WindowManager.LayoutParams {

    val frameHeight = if (Build.VERSION.SDK_INT == android.os.Build.VERSION_CODES.Q) {

        displayY(context) + navigationBarHeight(context)

    } else {

        displayY(context) + navigationBarHeight(context) + statusBarHeight(context)

    }

    val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
        /* Width */ displayX(context),
        /* Height */ frameHeight,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        PixelFormat.TRANSLUCENT
    )
    layoutParams.gravity = Gravity.TOP or Gravity.START
    layoutParams.x = 0
    layoutParams.y = -(statusBarHeight(context))
    layoutParams.windowAnimations = android.R.style.Animation_Dialog

    return layoutParams
}

fun createOverlyLayout(context: AppCompatActivity, notificationsCreator: NotificationsCreator, rootView: ConstraintLayout,
                       frameUrl: String, frameUrlHorizontal: String) {

    notificationsCreator.playNotificationSound(context, R.raw.titan)
    notificationsCreator.doVibrate(context, 73)

    if (!OverlyFrame.Framing) {

        val confirmDialogue = ConfirmDialogue(context = context, viewGroup = rootView)
        confirmDialogue.initialize(dialogueTitle = context.getString(R.string.floatingPanelTitle), dialogueDescription = context.getString(R.string.floatingPanelDescription))
            .show(object : ConfirmDialogueInterface {

                override fun confirmed() {}

                override fun dismissed() {}

            })

        context.startForegroundService(Intent(context, OverlyFrame::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(FramePreview.IntentKeys.FrameUrl, frameUrl)
            putExtra(FramePreview.IntentKeys.FrameUrlHorizontal, frameUrlHorizontal)
        })

    } else {

        val confirmDialogue = ConfirmDialogue(context = context, viewGroup = rootView)
        confirmDialogue.initialize(dialogueTitle = context.getString(R.string.floatingPanelTitle), dialogueDescription = context.getString(R.string.removeFloatingPanelDescription))
            .show(object : ConfirmDialogueInterface {

                override fun confirmed() {

                    context.stopService(Intent(context, OverlyFrame::class.java))

                }

                override fun dismissed() {}

            })

    }

}