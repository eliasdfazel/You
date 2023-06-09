/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/14/23, 6:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Boot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Dashboard.UI.Frames.Preview.FramePreview
import co.geeksempire.frames.you.Database.IO.FrameIO
import co.geeksempire.frames.you.Overlay.OverlayFrame
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Notifications.NotificationsCreator
import co.geeksempire.frames.you.Utils.Settings.SystemSettings

class OpenOnBoot : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val systemSettings = SystemSettings(applicationContext)

        if (systemSettings.accessibilityServiceEnabled()
            && systemSettings.floatingPermissionEnabled()) {

            val notificationsCreator = NotificationsCreator()

            notificationsCreator.playNotificationSound(this@OpenOnBoot, R.raw.titan)
            notificationsCreator.doVibrate(applicationContext, 73)

            if (!OverlayFrame.Framing) {

                val frameIO = FrameIO(applicationContext)

                val frameUrl = frameIO.selectedFrame()
                val frameUrlHorizontal = frameIO.selectedFrameHorizontal()

                startForegroundService(Intent(applicationContext, OverlayFrame::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    putExtra(FramePreview.IntentKeys.FrameUrl, frameUrl)
                    putExtra(FramePreview.IntentKeys.FrameUrlHorizontal, frameUrlHorizontal)
                })

            }

        }

        this@OpenOnBoot.finish()

    }

}