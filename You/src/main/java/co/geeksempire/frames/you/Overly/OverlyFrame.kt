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