/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 7:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.Dialogue

import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.databinding.ConfirmationLayoutBinding

interface ConfirmDialogueInterface {
    fun confirmed()
    fun dismissed()
}

class ConfirmDialogue (private val context: AppCompatActivity, private val viewGroup: ConstraintLayout) {

    private val confirmationLayoutBinding = ConfirmationLayoutBinding.inflate(context.layoutInflater)

    fun initialize(dialogueTitle: String, dialogueDescription: String) : ConfirmDialogue {

        viewGroup.addView(confirmationLayoutBinding.root)

        confirmationLayoutBinding.root.visibility = View.INVISIBLE

        val confirmDialogueParameters = confirmationLayoutBinding.root.layoutParams as ConstraintLayout.LayoutParams
        confirmDialogueParameters.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        confirmDialogueParameters.height = ConstraintLayout.LayoutParams.MATCH_PARENT
        confirmationLayoutBinding.root.layoutParams = confirmDialogueParameters

        confirmationLayoutBinding.confirmTitle.text = dialogueTitle

        confirmationLayoutBinding.confirmDescription.text = Html.fromHtml(dialogueDescription, Html.FROM_HTML_MODE_COMPACT)

        confirmationLayoutBinding.rootView.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
        }

        return this@ConfirmDialogue
    }

    fun show(confirmDialogueInterface: ConfirmDialogueInterface) {

        confirmationLayoutBinding.rootView.setOnClickListener {

            dismiss()

        }

        confirmationLayoutBinding.confirmTitle.setOnClickListener {  }

        confirmationLayoutBinding.contentWrapper.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up))

        confirmationLayoutBinding.confirmButton.setOnClickListener {

            confirmDialogueInterface.confirmed()

            dismiss()

        }

        confirmationLayoutBinding.dismissButton.setOnClickListener {

            confirmDialogueInterface.dismissed()

            dismiss()

        }

    }

    private fun dismiss() {

        Handler(Looper.getMainLooper()).postDelayed({

            confirmationLayoutBinding.contentWrapper.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_down))

            confirmationLayoutBinding.rootView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))

            viewGroup.removeView(confirmationLayoutBinding.root)

        }, 333)

    }

}