/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/22/23, 7:41 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Database.IO

import android.content.Context
import co.geeksempire.frames.you.Database.Preferences.PreferencesIO

class FrameIO (private val context: Context) {

    object Key {
        const val SelectedFrame = "SelectedFrame"
    }

    private val preferencesIO = PreferencesIO(context, "Frames")

    fun selectedFrame(frameUrl: String) {

        preferencesIO.savePreference(FrameIO.Key.SelectedFrame, frameUrl)

    }

    fun selectedFrame() : String {

        return preferencesIO.readPreference(FrameIO.Key.SelectedFrame, "")
    }

}