/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/20/23, 7:01 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.Filters

import android.content.Context
import co.geeksempire.frames.you.Database.IO.DataIO
import co.geeksempire.frames.you.Database.Structure.DataStructure
import co.geeksempire.frames.you.databinding.DashboardLayoutBinding

class FilterFrames (private val context: Context, private val dashboardLayoutBinding: DashboardLayoutBinding, private val dataIO: DataIO) {

    fun initialize(allFrames: ArrayList<DataStructure>) {

        dashboardLayoutBinding.filterBar.newFrames.setOnClickListener {

            dataIO.filterNewFrames(allFrames)

        }

        dashboardLayoutBinding.filterBar.favoriteFrames.setOnClickListener {

            dataIO.filterFavoriteFrames(context, allFrames)

        }

        dashboardLayoutBinding.filterBar.hotFrames.setOnClickListener {

            dataIO.filterHotFrames(allFrames)

        }

    }

}