/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/18/23, 9:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.frames.you.databinding.EntryLayoutBinding

class EntryConfigurations : AppCompatActivity(), NetworkConnectionListenerInterface {

    private val networkCheckpoint: NetworkCheckpoint by lazy {
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



    }

    override fun networkAvailable() {

    }

    override fun networkLost() {

    }

}