/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/23/23, 6:46 AM
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
        const val SelectedFrameHorizontal = "SelectedFrameHorizontal"
    }

    private val preferencesIO = PreferencesIO(context, "Frames")

    fun selectedFrame(frameUrl: String) {

        preferencesIO.savePreference(FrameIO.Key.SelectedFrame, frameUrl)

    }

    fun selectedFrame() : String {

        return preferencesIO.readPreference(FrameIO.Key.SelectedFrame, "")
    }

    fun selectedFrameHorizontal(frameUrl: String) {

        preferencesIO.savePreference(FrameIO.Key.SelectedFrameHorizontal, frameUrl)

    }

    fun selectedFrameHorizontal() : String {

        return preferencesIO.readPreference(FrameIO.Key.SelectedFrameHorizontal, "")
    }

}