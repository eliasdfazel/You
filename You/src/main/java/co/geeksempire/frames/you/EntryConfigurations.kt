/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/18/23, 6:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Dashboard.UI.Dashboard
import co.geeksempire.frames.you.Database.IO.DisplayIO
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.frames.you.databinding.EntryLayoutBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class EntryConfigurations : AppCompatActivity(), NetworkConnectionListenerInterface {

    private val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@EntryConfigurations, entryLayoutBinding.rootView, networkCheckpoint)
    }

    private lateinit var entryLayoutBinding: EntryLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entryLayoutBinding = EntryLayoutBinding.inflate(layoutInflater)
        setContentView(entryLayoutBinding.root)

        networkConnectionListener.networkConnectionListenerInterface = this@EntryConfigurations

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            DisplayIO(applicationContext).apply {
                displayHeight(this@EntryConfigurations)
            }
        }

        if (BuildConfig.VERSION_NAME.contains("Beta")) {
            Firebase.messaging.subscribeToTopic("Beta")
        }

        startActivity(Intent(this@EntryConfigurations, Dashboard::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
            ActivityOptions.makeCustomAnimation(applicationContext, R.anim.fade_in, 0).toBundle())

    }

    override fun networkAvailable() {

    }

    override fun networkLost() {

    }

}