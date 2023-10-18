/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 10/18/23, 8:31 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.PackageInformation

import android.content.Context
import co.geeksempire.frames.you.BuildConfig
import co.geeksempire.frames.you.Database.Preferences.PreferencesIO

class PackageInformation (private val context: Context)  {

    object Key {
        const val Version = "Version"
    }

    private val preferencesIO = PreferencesIO(context, "Display")

    fun saveCurrentVersion() {

        preferencesIO.savePreference(PackageInformation.Key.Version, BuildConfig.VERSION_CODE)

    }

    fun readCurrentVersion() : Int {

        return preferencesIO.readPreference(PackageInformation.Key.Version, 0)
    }

}