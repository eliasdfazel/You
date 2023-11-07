/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 11/7/23, 2:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Profile.Points.IO

import android.content.Context
import co.geeksempire.frames.you.Database.Preferences.PreferencesIO

class PointsIO (private val context: Context) {

    object Key {
        const val Points = "Points"
    }

    private val preferencesIO = PreferencesIO(context, "Points")

    fun storePoints() {

        val earnedPoint: Int = storedPoints()

        preferencesIO.savePreference(PointsIO.Key.Points, earnedPoint + 1)

    }

    fun storedPoints() : Int {

        return preferencesIO.readPreference(PointsIO.Key.Points, 0)
    }

}