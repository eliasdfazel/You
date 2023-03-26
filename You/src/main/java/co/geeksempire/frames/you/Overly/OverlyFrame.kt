/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/26/23, 5:03 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Overly

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import co.geeksempire.frames.you.Dashboard.UI.Frames.Preview.FramePreview
import co.geeksempire.frames.you.R
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

class OverlyFrame : Service() {

    companion object {
        var Framing = false
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
                        Log.d(this@OverlyFrame.javaClass.simpleName, "-> Portrait <-")

                        layoutParameters = generateLayoutParameters(applicationContext)

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
                        Log.d(this@OverlyFrame.javaClass.simpleName, "-> Landscape <-")

                        layoutParameters = generateLayoutParametersHorizontal(applicationContext)

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

                layoutParameters = generateLayoutParameters(applicationContext)

                windowManager.addView(overlyLayoutBinding.root, layoutParameters)

                frameUrl = intent.getStringExtra(FramePreview.IntentKeys.FrameUrl)!!
                frameUrlHorizontal = intent.getStringExtra(FramePreview.IntentKeys.FrameUrlHorizontal)!!

                downloadFrame(frameUrl)

                if (!OverlyFrame.Framing) {

                    OverlyFrame.Framing = true

                }

            } else {

                stopSelf()

            }

        }

        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(333, notificationsCreator.bindNotification(applicationContext))

    }

    override fun onDestroy() {
        super.onDestroy()

        OverlyFrame.Framing = false

        windowManager.removeView(overlyLayoutBinding.root)

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

                            overlyLayoutBinding.frame.setShapeDrawable(resource)

                            overlyLayoutBinding.frame.visibility = View.VISIBLE
                            overlyLayoutBinding.frame.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

                        }

                    }

                    return false
                }

            })
            .submit()

    }

}