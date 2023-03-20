/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/20/23, 6:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.IO

import android.content.Context
import co.geeksempire.frames.you.Database.Preferences.PreferencesIO

class FavoriteIO (private val context: Context) {

    val preferencesIO = PreferencesIO(context, "Favorites")

    fun favoritIt(frameName: String) {

        preferencesIO.savePreference(frameName, true)

    }

    fun deFavoritIt(frameName: String) {

        preferencesIO.savePreference(frameName, false)

    }

    fun favorited(frameName: String) : Boolean {

        return preferencesIO.readPreference(frameName)
    }

}