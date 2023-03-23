/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/23/23, 6:56 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI.Frames.Preview

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Dashboard.UI.Frames.Preview.Extensions.setupUserInterface
import co.geeksempire.frames.you.Database.IO.FavoriteIO
import co.geeksempire.frames.you.Database.IO.FrameIO
import co.geeksempire.frames.you.Overly.OverlyFrame
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Display.displayRatio
import co.geeksempire.frames.you.databinding.FramesPreviewLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase

class FramePreview : AppCompatActivity() {

    object IntentKeys {
        const val FrameUrl = "FrameUrl"
        const val FrameUrlHorizontal = "FrameUrlHorizontal"
        const val FrameTrend = "FrameTrend"
        const val FrameName = "FrameName"
    }

    private val favoriteIO by lazy {
        FavoriteIO(applicationContext)
    }

    private val frameIO by lazy {
        FrameIO(applicationContext)
    }

    lateinit var framesPreviewLayoutBinding: FramesPreviewLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        framesPreviewLayoutBinding = FramesPreviewLayoutBinding.inflate(layoutInflater)
        setContentView(framesPreviewLayoutBinding.root)

        setupUserInterface()

        if (intent.hasExtra(FramePreview.IntentKeys.FrameUrl)
            && intent.hasExtra(FramePreview.IntentKeys.FrameTrend)
            && intent.hasExtra(FramePreview.IntentKeys.FrameName)) {

            intent.getStringExtra(FramePreview.IntentKeys.FrameUrl)?.let { frameUrl ->

                val frameUrlHorizontal = intent.getStringExtra(FramePreview.IntentKeys.FrameUrlHorizontal)!!

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

                val frameName = intent.getStringExtra(FramePreview.IntentKeys.FrameName)!!

                val frameTrend = intent.getIntExtra(FramePreview.IntentKeys.FrameTrend, 1)

                framesPreviewLayoutBinding.confirmBar.confirmFrames.setOnClickListener {

                    if (OverlyFrame.Framing) {

                        stopService(Intent(applicationContext, OverlyFrame::class.java))

                    }

                    frameIO.selectedFrame(frameUrl)
                    frameIO.selectedFrameHorizontal(frameUrlHorizontal)

                    Handler(Looper.getMainLooper()).postDelayed({

                        startForegroundService(Intent(applicationContext, OverlyFrame::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            putExtra(FramePreview.IntentKeys.FrameUrl, frameUrl)
                            putExtra(FramePreview.IntentKeys.FrameUrlHorizontal, frameUrlHorizontal)
                        })

                        Firebase.functions
                            .getHttpsCallable("updateFrameTrends")
                            .call(hashMapOf(
                                "documentPath" to "/You/Frames/${displayRatio(applicationContext)}/${frameUrl}",
                                "frameTrend" to (frameTrend + 1)
                            ))

                        this@FramePreview.finish()

                    }, 333)

                }

                framesPreviewLayoutBinding.confirmBar.favoriteFrames.setOnClickListener {

                    if (favoriteIO.favorited(frameName)) {

                        favoriteIO.favoritIt(frameName)

                    } else {

                        favoriteIO.deFavoritIt(frameName)

                    }

                }

                framesPreviewLayoutBinding.confirmBar.declineFrames.setOnClickListener {

                    this@FramePreview.finish()

                }

            }

        }

    }

    override fun onPause() {
        super.onPause()

        overridePendingTransition(0, R.anim.fade_out)

    }

}