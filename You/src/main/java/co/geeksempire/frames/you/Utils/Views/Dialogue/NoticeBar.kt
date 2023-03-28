/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/28/23, 4:53 AM
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
import co.geeksempire.frames.you.Utils.Display.dpToInteger
import co.geeksempire.frames.you.databinding.NoticeBarLayoutBinding

interface NoticeInterface {
    fun noticeAction()
}

class NoticeBar (private val context: AppCompatActivity, private val viewGroup: ConstraintLayout) {

    private val noticeBarLayoutBinding = NoticeBarLayoutBinding.inflate(context.layoutInflater)

    fun initialize(noticeDescription: String, noticeActionText: String = "Yes") : NoticeBar {

        viewGroup.addView(noticeBarLayoutBinding.root)

        noticeBarLayoutBinding.root.visibility = View.INVISIBLE

        val noticeBarParameters = noticeBarLayoutBinding.root.layoutParams as ConstraintLayout.LayoutParams
        noticeBarParameters.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
        noticeBarParameters.width = ConstraintLayout.LayoutParams.MATCH_PARENT
        noticeBarParameters.bottomMargin = dpToInteger(context, 73)
        noticeBarLayoutBinding.root.layoutParams = noticeBarParameters

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

        Handler(Looper.getMainLooper()).postDelayed({

            noticeBarLayoutBinding.rootView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_down))

            viewGroup.removeView(noticeBarLayoutBinding.root)

        }, 333)

    }

}