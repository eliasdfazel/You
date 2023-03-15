/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/13/23, 6:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Boot

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (context != null) {
            Log.d(this@BootReceiver.javaClass.simpleName.toString(), "Boot Completed")

            intent?.let {

                if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

                    context.startActivity(Intent(context, OpenOnBoot::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    })

                }

            }

        }

    }

}