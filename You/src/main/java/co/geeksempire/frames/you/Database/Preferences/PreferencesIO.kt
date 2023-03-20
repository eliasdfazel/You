/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/20/23, 6:57 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.Preferences

import android.content.Context

class PreferencesIO (private val context: Context, private val preferencesName: String = "Preferences") {

    fun savePreference(key: String, value: Boolean) {

        context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).apply {

            edit().apply {

                putBoolean(key, value)
                apply()

            }

        }

    }

    fun readPreference(key: String) : Boolean {

        return context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).getBoolean(key, false)
    }

}