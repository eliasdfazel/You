/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/3/23, 7:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI.Frames.Preview

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
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
import co.geeksempire.frames.you.Utils.Operations.generateCreatorIcon
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

        const val CreatorName = "CreatorName"
        const val CreatorUrl = "CreatorUrl"
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
            && intent.hasExtra(FramePreview.IntentKeys.FrameName)
            && intent.hasExtra(FramePreview.IntentKeys.CreatorName)
            && intent.hasExtra(FramePreview.IntentKeys.CreatorUrl)) {

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

                                    framesPreviewLayoutBinding.informationBar.root.visibility = View.VISIBLE
                                    framesPreviewLayoutBinding.informationBar.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

                                }

                            }

                            return false
                        }

                    })
                    .submit()

                val frameName = intent.getStringExtra(FramePreview.IntentKeys.FrameName)!!

                val frameTrend = intent.getIntExtra(FramePreview.IntentKeys.FrameTrend, 1)

                setupCreatorInformation(intent.getStringExtra(FramePreview.IntentKeys.CreatorName)!!,
                    intent.getStringExtra(FramePreview.IntentKeys.CreatorUrl)!!)

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

    private fun setupCreatorInformation(creatorName: String, creatorUrl: String) {

        framesPreviewLayoutBinding.informationBar.creatorName.text = creatorName

        Glide.with(applicationContext)
            .asDrawable()
            .load(generateCreatorIcon(creatorUrl))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        runOnUiThread {

                            framesPreviewLayoutBinding.informationBar.creatorIconBackground.setImageDrawable(resource)

                            framesPreviewLayoutBinding.informationBar.creatorIcon.setImageDrawable(resource)

                            framesPreviewLayoutBinding.informationBar.root.setOnClickListener {

                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(creatorUrl)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                                    ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

                            }

                        }

                    }

                    return false
                }

            })
            .submit()

    }

}