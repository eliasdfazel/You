/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 6:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Boot

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Overly.OverlyFrame
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

            if (!OverlyFrame.Framing) {

                startForegroundService(Intent(applicationContext, OverlyFrame::class.java))

            }

        }

        this@OpenOnBoot.finish()

    }

}