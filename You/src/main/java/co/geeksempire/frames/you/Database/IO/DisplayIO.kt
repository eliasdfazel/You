/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/12/23, 11:46 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.IO

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.Database.Preferences.PreferencesIO

class DisplayIO (private val context: Context) {

    object Key {
        const val Height = "Height"
        const val Width = "Width"
    }

    private val preferencesIO = PreferencesIO(context, "Display")

    @RequiresApi(Build.VERSION_CODES.R)
    fun displayHeight(context: AppCompatActivity) {

        val windowMetrics = context.windowManager.currentWindowMetrics

        val rect = windowMetrics.bounds

        preferencesIO.savePreference(DisplayIO.Key.Height, rect.bottom)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun displayHeight(defaultValue: Int) : Int {

        return preferencesIO.readPreference(DisplayIO.Key.Height, defaultValue)
    }

}