/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 6:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import co.geeksempire.frames.you.R


class NotificationsCreator {

    fun bindNotification(context: Context) : Notification {

        val notificationBuilder = NotificationCompat.Builder(context, context.packageName)

        notificationBuilder.setContentTitle(context.resources.getString(R.string.applicationName))
        notificationBuilder.setContentText(context.resources.getString(R.string.applicationDescription))
        notificationBuilder.setTicker(context.resources.getString(R.string.applicationName))
        notificationBuilder.setSmallIcon(R.drawable.notification_icon)
        notificationBuilder.setAutoCancel(false)
        notificationBuilder.color = context.getColor(R.color.primaryColorPurple)

        val notificationChannel = NotificationChannel(context.packageName, context.resources.getString(R.string.applicationName), NotificationManager.IMPORTANCE_LOW)
        notificationChannel.description = context.resources.getString(R.string.applicationName)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        return notificationBuilder.build()

    }

    fun playNotificationSound(activity: AppCompatActivity, soundId: Int) {

        val mediaPlayer: MediaPlayer = MediaPlayer.create(activity, soundId)
        mediaPlayer.setVolume(0.13f, 0.13f)
        mediaPlayer.setOnCompletionListener { aMediaPlayer ->

            aMediaPlayer.reset()
            aMediaPlayer.release()

        }
        mediaPlayer.start()

    }

    fun doVibrate(context: Context, millisecondVibrate: Long) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager

            vibratorManager.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(millisecondVibrate, VibrationEffect.DEFAULT_AMPLITUDE)))

        } else {

            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            vibrator.vibrate(VibrationEffect.createOneShot(millisecondVibrate, VibrationEffect.DEFAULT_AMPLITUDE))

        }

    }

}