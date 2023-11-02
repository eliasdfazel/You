/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/18/23, 7:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Overlay

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import co.geeksempire.frames.you.Dashboard.UI.Frames.Preview.FramePreview
import co.geeksempire.frames.you.Database.IO.DisplayIO
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Animations.circularHide
import co.geeksempire.frames.you.Utils.Colors.allPrimaryColors
import co.geeksempire.frames.you.Utils.Colors.uniqueGradient
import co.geeksempire.frames.you.Utils.Display.displayX
import co.geeksempire.frames.you.Utils.Display.displayY
import co.geeksempire.frames.you.Utils.Notifications.NotificationsCreator
import co.geeksempire.frames.you.databinding.OverlyLayoutBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OverlayFrame : Service() {

    companion object {
        var Framing = false
    }

    private val displayIO: DisplayIO by lazy {
        DisplayIO(applicationContext)
    }

    private val notificationsCreator = NotificationsCreator()

    private val windowManager: WindowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val layoutInflater: LayoutInflater by lazy {
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private val overlyLayoutBinding: OverlyLayoutBinding by lazy {
        OverlyLayoutBinding.inflate(layoutInflater)
    }

    private var layoutParameters = WindowManager.LayoutParams()

    var frameUrl: String = ""
    var frameUrlHorizontal: String = ""

    override fun onBind(intent: Intent?): IBinder? { return null }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        when (newConfig.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> {

                try {

                    if (overlyLayoutBinding.root.isShown
                        && frameUrl.isNotEmpty()
                        && frameUrlHorizontal.isNotEmpty()) {
                        Log.d(this@OverlayFrame.javaClass.simpleName, "-> Portrait <-")

                        layoutParameters = generateLayoutParameters(applicationContext, displayIO)

                        windowManager.updateViewLayout(overlyLayoutBinding.root, layoutParameters)

                        overlyLayoutBinding.frame.rotation = 0f

                        downloadFrame(frameUrl)

                    }

                } catch (e: WindowManager.InvalidDisplayException) {
                    e.printStackTrace()
                }

            }
            Configuration.ORIENTATION_LANDSCAPE -> {

                try {

                    if (overlyLayoutBinding.root.isShown
                        && frameUrl.isNotEmpty()
                        && frameUrlHorizontal.isNotEmpty()) {
                        Log.d(this@OverlayFrame.javaClass.simpleName, "-> Landscape <-")

                        layoutParameters = generateLayoutParametersHorizontal(applicationContext, displayIO)

                        windowManager.updateViewLayout(overlyLayoutBinding.root, layoutParameters)

                        downloadFrame(frameUrlHorizontal)

                    }

                } catch (e: WindowManager.InvalidDisplayException) {
                    e.printStackTrace()
                }

            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {
        super.onStartCommand(intent, flags, startId)

        intent?.let {

            if (intent.hasExtra(FramePreview.IntentKeys.FrameUrl)
                && intent.hasExtra(FramePreview.IntentKeys.FrameUrlHorizontal)) {

                layoutParameters = generateLayoutParameters(applicationContext, displayIO)

                windowManager.addView(overlyLayoutBinding.root, layoutParameters)

                frameUrl = intent.getStringExtra(FramePreview.IntentKeys.FrameUrl)!!
                frameUrlHorizontal = intent.getStringExtra(FramePreview.IntentKeys.FrameUrlHorizontal)!!

                initializeOverlyFrame(frameUrl)

                if (!OverlayFrame.Framing) {

                    OverlayFrame.Framing = true

                }

            } else {

                stopSelf()

            }

        }

        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(333, notificationsCreator.bindNotification(applicationContext), ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC)
        } else {
            startForeground(333, notificationsCreator.bindNotification(applicationContext))
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        OverlayFrame.Framing = false

        windowManager.removeView(overlyLayoutBinding.root)

    }

    private fun initializeOverlyFrame(frameUrl: String) {

        val primaryColors = allPrimaryColors(applicationContext)

        val randomColors = uniqueGradient(primaryColors)

        overlyLayoutBinding.framePlaceholder.background = GradientDrawable(GradientDrawable.Orientation.TL_BR, randomColors)

        overlyLayoutBinding.framePlaceholder.visibility = View.VISIBLE
        overlyLayoutBinding.framePlaceholder.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

        Handler(Looper.getMainLooper()).postDelayed({

            downloadFrame(frameUrl)

        }, 975)

    }

    private fun downloadFrame(inputFrameUrl: String?) {

        Glide.with(applicationContext)
            .asDrawable()
            .load(inputFrameUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {

                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                    resource?.let {

                        CoroutineScope(Dispatchers.Main).launch {

                            overlyLayoutBinding.frame.background = (resource)

                            circularHide(viewInstance = overlyLayoutBinding.framePlaceholder,
                                centerX = displayX(applicationContext) / 2, centerY = displayY(applicationContext) / 2,
                                initialDuration = 1357
                            )

                        }

                    }

                    return false
                }

            })
            .submit()

    }



}