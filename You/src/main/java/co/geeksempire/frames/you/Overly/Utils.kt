/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 7:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Overly

import android.content.Intent
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Notifications.NotificationsCreator
import co.geeksempire.frames.you.Utils.Views.Dialogue.ConfirmDialogue
import co.geeksempire.frames.you.Utils.Views.Dialogue.ConfirmDialogueInterface

fun generateLayoutParameters(height: Int, width: Int) : WindowManager.LayoutParams {

    val layoutParams: WindowManager.LayoutParams = WindowManager.LayoutParams(
        width,
        height,
        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        PixelFormat.TRANSLUCENT
    )
    layoutParams.gravity = Gravity.TOP or Gravity.START
    layoutParams.x = 0
    layoutParams.y = 0
    layoutParams.windowAnimations = android.R.style.Animation_Dialog

    return layoutParams
}

fun createOverlyLayout(context: AppCompatActivity, notificationsCreator: NotificationsCreator, rootView: ConstraintLayout) {

    notificationsCreator.playNotificationSound(context, R.raw.titan)
    notificationsCreator.doVibrate(context, 73)

    if (!OverlyFrame.Framing) {

        val confirmDialogue = ConfirmDialogue(context = context, viewGroup = rootView)
        confirmDialogue.initialize(dialogueTitle = context.getString(R.string.floatingPanelTitle), dialogueDescription = context.getString(R.string.floatingPanelDescription))
            .show(object : ConfirmDialogueInterface {

                override fun confirmed() {}

                override fun dismissed() {}

            })

        context.startForegroundService(Intent(context, OverlyFrame::class.java))

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