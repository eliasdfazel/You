/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/20/23, 5:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.Frames.Adapter

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.frames.you.Database.Structure.DataStructure
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.databinding.FrameItemLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FramesAdapter (private val context: AppCompatActivity) : RecyclerView.Adapter<FramesViewHolder>() {

    val framesItems = ArrayList<DataStructure>()

    override fun getItemCount(): Int {

        return framesItems.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) : FramesViewHolder {

        return FramesViewHolder(FrameItemLayoutBinding.inflate(context.layoutInflater, viewGroup, false))
    }

    override fun onBindViewHolder(framesViewHolder: FramesViewHolder, position: Int) {

        val rippleDrawable = context.getDrawable(R.drawable.ripple_effect_unbound) as RippleDrawable
        rippleDrawable.setColor(ColorStateList.valueOf(framesItems[position].backgroundColors.random()))
        framesViewHolder.frameBackground.setImageDrawable(rippleDrawable)

        framesViewHolder.frameBackground.background = GradientDrawable(GradientDrawable.Orientation.TL_BR, framesItems[position].backgroundColors)

        Glide.with(context)
            .asDrawable()
            .load(framesItems[position].frameUrl)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        context.runOnUiThread {

                             framesViewHolder.frameForeground.background = resource

                        }

                    }

                    return false
                }

            })
            .submit()

        framesViewHolder.frameBackground.setOnClickListener {

            /*Firebase.firestore
                .document("/You/Frames/${displayRatio(context)}/${framesItems[position].frameName}")
                .update(
                    "frameTrend", (framesItems[position].frameTrend + 1)
                )*/

            // Call Cloud Function To Update

        }

    }

}