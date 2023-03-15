/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/15/23, 7:41 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Overly.OverlyFrame
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.frames.you.databinding.EntryLayoutBinding

class EntryConfigurations : AppCompatActivity(), NetworkConnectionListenerInterface {

    val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@EntryConfigurations, entryLayoutBinding.rootView, networkCheckpoint)
    }

    lateinit var entryLayoutBinding: EntryLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryLayoutBinding = EntryLayoutBinding.inflate(layoutInflater)
        setContentView(entryLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@EntryConfigurations

        Handler(Looper.getMainLooper()).postDelayed({

            startForegroundService(Intent(this@EntryConfigurations, OverlyFrame::class.java))

        }, 1357)

    }

    override fun networkAvailable() {

    }

    override fun networkLost() {

    }

}