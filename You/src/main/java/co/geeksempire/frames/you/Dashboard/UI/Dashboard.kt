/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/30/23, 5:51 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.frames.you.Dashboard.Extensions.setupUserInterface
import co.geeksempire.frames.you.Dashboard.Filters.FilterFrames
import co.geeksempire.frames.you.Dashboard.UI.Frames.Adapter.FramesAdapter
import co.geeksempire.frames.you.Database.IO.DataIO
import co.geeksempire.frames.you.Database.Structure.DataStructure
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Utils.Display.columnCount
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkCheckpoint
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListener
import co.geeksempire.frames.you.Utils.NetworkConnections.NetworkConnectionListenerInterface
import co.geeksempire.frames.you.Utils.Settings.SystemSettings
import co.geeksempire.frames.you.Utils.Views.Dialogue.NoticeBar
import co.geeksempire.frames.you.Utils.Views.Dialogue.NoticeInterface
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

    private val dataIO: DataIO by lazy {
        ViewModelProvider(this@Dashboard)[DataIO::class.java]
    }

    private val framesAdapter by lazy {
        FramesAdapter(this@Dashboard)
    }

    val allUntouchedFrames = ArrayList<DataStructure>()

    lateinit var dashboardLayoutBinding: DashboardLayoutBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardLayoutBinding = DashboardLayoutBinding.inflate(layoutInflater)
        setContentView(dashboardLayoutBinding.root)

        window.decorView.setBackgroundColor(getColor(R.color.black))

        networkConnectionListener.networkConnectionListenerInterface = this@Dashboard

        dashboardLayoutBinding.frameRecyclerView.layoutManager = GridLayoutManager(applicationContext, columnCount(applicationContext, 159), RecyclerView.VERTICAL, false)
        dashboardLayoutBinding.frameRecyclerView.adapter = framesAdapter

        dataIO.allFrames.observe(this@Dashboard) {
            Log.d(this@Dashboard.javaClass.simpleName, "Frames #${it.size}")

            if (it.isNotEmpty()) {

                if (allUntouchedFrames.isEmpty()) {

                    allUntouchedFrames.addAll(it)

                }

                framesAdapter.framesItems.clear()

                framesAdapter.framesItems.addAll(it)

                framesAdapter.notifyDataSetChanged()

                dashboardLayoutBinding.waiting.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                dashboardLayoutBinding.waiting.visibility = View.GONE

                Handler(Looper.getMainLooper()).postDelayed({

                    dashboardLayoutBinding.filterBar.root.visibility = View.VISIBLE
                    dashboardLayoutBinding.filterBar.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))

                    FilterFrames(applicationContext, dashboardLayoutBinding, dataIO)
                        .initialize(allUntouchedFrames)

                }, 333)

            } else {

                NoticeBar(this@Dashboard, dashboardLayoutBinding.root)
                    .initialize(getString(R.string.errorOccurred), noticeActionText = getString(R.string.retry))
                    .show(object : NoticeInterface {

                        override fun noticeAction() {

                            if (allUntouchedFrames.isEmpty()) {

                                dataIO.retrieveFrames(applicationContext)

                            }

                        }

                    })

            }

        }

    }

    override fun onResume() {
        super.onResume()

        setupUserInterface()

        if (systemSettings.floatingPermissionEnabled()) {

            dashboardLayoutBinding.floatingPermission.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
            dashboardLayoutBinding.floatingPermission.root.visibility = View.GONE

            dashboardLayoutBinding.searchBar.root.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in))
            dashboardLayoutBinding.searchBar.root.visibility = View.VISIBLE

            dashboardLayoutBinding.searchBar.searchQuery.setOnEditorActionListener { view, actionId, keyEvent ->

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    val searchQuery = dashboardLayoutBinding.searchBar.searchQuery.text.toString()

                    dataIO.searchFrames(allUntouchedFrames, searchQuery)

                }

                false
            }

            if (networkCheckpoint.networkConnection()) {

                this@Dashboard.networkAvailable()

            } else {

                this@Dashboard.networkLost()

            }

        } else {

            dashboardLayoutBinding.waiting.visibility = View.GONE

            NoticeBar(this@Dashboard, dashboardLayoutBinding.root)
                .initialize(getString(R.string.floatingNotice), noticeActionText = getString(R.string.ok), noticeActionTint = getColor(R.color.primaryColorOrange))
                .show(object : NoticeInterface {

                    override fun noticeAction() {



                    }

                })

        }

    }

    override fun networkAvailable() {

        if (systemSettings.floatingPermissionEnabled()) {

            if (allUntouchedFrames.isEmpty()) {

                if (!DataIO.busyDataIO) {

                    dataIO.retrieveFrames(applicationContext)


                }

            }

        }

    }

    override fun networkLost() {

        NoticeBar(this@Dashboard, dashboardLayoutBinding.root)
            .initialize(getString(R.string.noInternet), noticeActionText = getString(R.string.retry))
            .show(object : NoticeInterface {

                override fun noticeAction() {

                    startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                }

            })

    }

}