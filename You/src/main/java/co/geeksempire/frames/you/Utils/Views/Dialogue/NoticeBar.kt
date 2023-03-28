/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/28/23, 5:07 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.Dialogue

import android.content.res.ColorStateList
import android.text.Html
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Display.dpToInteger
import co.geeksempire.frames.you.databinding.NoticeBarLayoutBinding

interface NoticeInterface {
    fun noticeAction()
}

class NoticeBar (private val context: AppCompatActivity, private val viewGroup: ConstraintLayout) {

    private val noticeBarLayoutBinding = NoticeBarLayoutBinding.inflate(context.layoutInflater)

    fun initialize(noticeDescription: String, noticeActionText: String = "Yes", noticeActionTint: Int = context.getColor(R.color.primaryColorRed)) : NoticeBar {

        viewGroup.addView(noticeBarLayoutBinding.root)

        noticeBarLayoutBinding.root.visibility = View.INVISIBLE

        val noticeBarParameters = noticeBarLayoutBinding.root.layoutParams as ConstraintLayout.LayoutParams
        noticeBarParameters.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        noticeBarParameters.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        noticeBarParameters.bottomMargin = dpToInteger(context, 73)
        noticeBarLayoutBinding.root.layoutParams = noticeBarParameters

        noticeBarLayoutBinding.noticeActionBackground.imageTintList = ColorStateList.valueOf(noticeActionTint)

        noticeBarLayoutBinding.noticeDescription.text = Html.fromHtml(noticeDescription, Html.FROM_HTML_MODE_COMPACT)
        noticeBarLayoutBinding.noticeActionText.text = noticeActionText

        return this@NoticeBar
    }

    fun show(noticeInterface: NoticeInterface) {

        noticeBarLayoutBinding.rootView.apply {
            visibility = View.VISIBLE
            startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_up))
        }

        noticeBarLayoutBinding.noticeActionBackground.setOnClickListener {

            noticeInterface.noticeAction()

            dismiss()

        }

    }

    private fun dismiss() {

        val fadeDownAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_down)

        noticeBarLayoutBinding.rootView.startAnimation(fadeDownAnimation)

        fadeDownAnimation.setAnimationListener(object : AnimationListener {

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {

                viewGroup.removeView(noticeBarLayoutBinding.root)

            }

            override fun onAnimationRepeat(animation: Animation?) {}

        })

    }

}