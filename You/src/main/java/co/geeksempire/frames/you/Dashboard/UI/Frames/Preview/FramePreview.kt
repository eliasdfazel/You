/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/22/23, 7:18 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI.Frames.Preview

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Dashboard.UI.Frames.Preview.Extensions.setupUserInterface
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.databinding.FramesPreviewLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class FramePreview : AppCompatActivity() {

    object IntentKeys {
        const val FrameUrl = "FrameUrl"
        const val FrameTrend = "FrameTrend"
    }

    lateinit var framesPreviewLayoutBinding: FramesPreviewLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        framesPreviewLayoutBinding = FramesPreviewLayoutBinding.inflate(layoutInflater)
        setContentView(framesPreviewLayoutBinding.root)

        setupUserInterface()

        if (intent.hasExtra(FramePreview.IntentKeys.FrameUrl)) {

            intent.getStringExtra(FramePreview.IntentKeys.FrameUrl)?.let { frameUrl ->

                Glide.with(applicationContext)
                    .asDrawable()
                    .load(frameUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                            resource?.let {

                                runOnUiThread {

                                    framesPreviewLayoutBinding.framePreview.background = resource

                                    framesPreviewLayoutBinding.confirmBar.root.visibility = View.VISIBLE
                                    framesPreviewLayoutBinding.confirmBar.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

                                }

                            }

                            return false
                        }

                    })
                    .submit()

                val frameTrend = intent.getIntExtra(Intent.EXTRA_PHONE_NUMBER, 1)

                framesPreviewLayoutBinding.confirmBar.confirmFrames.setOnClickListener {

//                    Firebase.functions
//                        .getHttpsCallable("updateFrameTrends")
//                        .call(hashMapOf(
//                            "documentPath" to "/You/Frames/${displayRatio(applicationContext)}/${frameUrl}",
//                            "frameTrend" to (frameTrend + 1)
//                        ))

                }

            }

        }

    }

}