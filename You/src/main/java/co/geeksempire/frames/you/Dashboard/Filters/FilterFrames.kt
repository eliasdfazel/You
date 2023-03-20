/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/20/23, 5:38 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.Filters

import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.databinding.DashboardLayoutBinding

class FilterFrames (private val context: AppCompatActivity, private val dashboardLayoutBinding: DashboardLayoutBinding) {

    fun initialize() {

        dashboardLayoutBinding.filterBar.newFrames.setOnClickListener {



        }

        dashboardLayoutBinding.filterBar.favoriteFrames.setOnClickListener {



        }

        dashboardLayoutBinding.filterBar.hotFrames.setOnClickListener {



        }

    }

}