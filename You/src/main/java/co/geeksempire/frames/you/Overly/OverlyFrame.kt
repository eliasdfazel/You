/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 6:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Overly

import android.app.Service
import android.content.Intent
import android.os.IBinder

class OverlyFrame : Service() {

    companion object {
        var Framing = false
    }

    override fun onBind(intent: Intent?): IBinder? { return null }

}