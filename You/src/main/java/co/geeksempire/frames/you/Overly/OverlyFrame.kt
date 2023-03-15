/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 7:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Overly

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.LayoutInflater
import android.view.WindowManager
import co.geeksempire.frames.you.Utils.Notifications.NotificationsCreator
import co.geeksempire.frames.you.databinding.OverlyLayoutBinding

class OverlyFrame : Service() {

    companion object {
        var Framing = false
    }

    private val notificationsCreator = NotificationsCreator()

    val windowManager: WindowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private val layoutInflater: LayoutInflater by lazy {
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    private val overlyLayoutBinding: OverlyLayoutBinding by lazy {
        OverlyLayoutBinding.inflate(layoutInflater)
    }

    private var layoutParameters = WindowManager.LayoutParams()

    override fun onBind(intent: Intent?): IBinder? { return null }

    @SuppressLint("ClickableViewAccessibility")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) : Int {
        super.onStartCommand(intent, flags, startId)

        layoutParameters = generateLayoutParameters(applicationContext, 73, 301)

        windowManager.addView(overlyLayoutBinding.root, layoutParameters)

        if (!OverlyFrame.Framing) {

            OverlyFrame.Framing = true

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

}