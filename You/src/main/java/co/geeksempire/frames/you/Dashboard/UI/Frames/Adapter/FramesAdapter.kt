/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/30/23, 6:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI.Frames.Adapter

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.frames.you.Dashboard.UI.Frames.Preview.FramePreview
import co.geeksempire.frames.you.Database.Structure.DataStructure
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Views.Glide.GlideApp
import co.geeksempire.frames.you.databinding.FrameItemLayoutBinding
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
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
        Log.d(this@FramesAdapter.javaClass.simpleName, framesItems[position].frameUrl)

        val rippleDrawable = context.getDrawable(R.drawable.ripple_effect_unbound) as RippleDrawable
        rippleDrawable.setColor(ColorStateList.valueOf(framesItems[position].backgroundColors.random()))
        framesViewHolder.frameBackground.setImageDrawable(rippleDrawable)

        framesViewHolder.frameBackground.background = GradientDrawable(GradientDrawable.Orientation.TL_BR, framesItems[position].backgroundColors)

        GlideApp.with(context)
            .asDrawable()
            .load(framesItems[position].frameUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return true
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        context.runOnUiThread {

                             framesViewHolder.frameForeground.background = resource

                        }

                    }

                    return true
                }

            })
            .submit()

        framesViewHolder.frameBackground.setOnClickListener {

            context.startActivity(Intent(context, FramePreview::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                putExtra(FramePreview.IntentKeys.FrameUrl, framesItems[position].frameUrl)
                putExtra(FramePreview.IntentKeys.FrameUrlHorizontal, framesItems[position].frameUrlHorizontal)
                putExtra(FramePreview.IntentKeys.FrameTrend, framesItems[position].frameTrend)
                putExtra(FramePreview.IntentKeys.FrameName, framesItems[position].frameName)

                putExtra(FramePreview.IntentKeys.CreatorName, framesItems[position].frameAuthorNickname)
                putExtra(FramePreview.IntentKeys.CreatorUrl, framesItems[position].frameAuthorLink)
            }, ActivityOptions.makeCustomAnimation(context, R.anim.fade_in, 0).toBundle())

        }

    }

}