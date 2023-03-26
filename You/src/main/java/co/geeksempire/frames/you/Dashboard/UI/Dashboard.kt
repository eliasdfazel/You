/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/26/23, 6:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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

    private val allUntouchedFrames = ArrayList<DataStructure>()

    lateinit var dashboardLayoutBinding: DashboardLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardLayoutBinding = DashboardLayoutBinding.inflate(layoutInflater)
        setContentView(dashboardLayoutBinding.root)

        window.decorView.setBackgroundColor(getColor(R.color.black))

        networkConnectionListener.networkConnectionListenerInterface = this@Dashboard

        setupUserInterface()

        dashboardLayoutBinding.frameRecyclerView.layoutManager = GridLayoutManager(applicationContext, columnCount(applicationContext, 159), RecyclerView.VERTICAL, false)
        dashboardLayoutBinding.frameRecyclerView.adapter = framesAdapter

        dataIO.allFrames.observe(this@Dashboard) {

            if (it.isNotEmpty()) {

                dashboardLayoutBinding.waiting.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out))
                dashboardLayoutBinding.waiting.visibility = View.GONE

                if (allUntouchedFrames.isEmpty()) {

                    allUntouchedFrames.addAll(it)

                }

                framesAdapter.framesItems.clear()

                framesAdapter.framesItems.addAll(it)

                framesAdapter.notifyItemRangeInserted(0, it.size)

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

            if (allUntouchedFrames.isEmpty()) {

                dataIO.retrieveFrames(applicationContext)

            }

        } else {

            dashboardLayoutBinding.waiting.visibility = View.INVISIBLE

        }

    }

    override fun networkAvailable() {}

    override fun networkLost() {}

}