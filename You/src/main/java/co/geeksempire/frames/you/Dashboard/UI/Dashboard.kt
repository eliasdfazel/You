/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/18/23, 11:25 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Dashboard.Extensions.setupUserInterface
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.frames.you.Utils.Settings.SystemSettings
import co.geeksempire.frames.you.databinding.DashboardLayoutBinding

class Dashboard : AppCompatActivity(), NetworkConnectionListenerInterface {

    val systemSettings: SystemSettings by lazy {
        SystemSettings(applicationContext)
    }

    private val networkCheckpoint: NetworkCheckpoint by lazy {
        NetworkCheckpoint(applicationContext)
    }

    private val networkConnectionListener: NetworkConnectionListener by lazy {
        NetworkConnectionListener(this@Dashboard, dashboardLayoutBinding.rootView, networkCheckpoint)
    }

    lateinit var dashboardLayoutBinding: DashboardLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardLayoutBinding = DashboardLayoutBinding.inflate(layoutInflater)
        setContentView(dashboardLayoutBinding.root)

        window.decorView.setBackgroundColor(getColor(R.color.black))

        networkConnectionListener.networkConnectionListenerInterface = this@Dashboard



    }

    override fun onResume() {
        super.onResume()

        setupUserInterface()

    }

    override fun networkAvailable() {

    }

    override fun networkLost() {

    }

}