/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/1/23, 7:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Settings

import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter
import co.geeksempire.frames.you.Stability.StabilityService

class SystemSettings (private val context: Context) {

    fun accessibilityServiceEnabled() : Boolean {

        val expectedComponentName = ComponentName(context, StabilityService::class.java)

        val enabledServicesSetting = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES) ?: return false

        val colonSplitter = SimpleStringSplitter(':')
        colonSplitter.setString(enabledServicesSetting)

        while (colonSplitter.hasNext()) {

            val componentNameString = colonSplitter.next()

            val enabledService = ComponentName.unflattenFromString(componentNameString)

            if (enabledService != null && enabledService == expectedComponentName) {

                return true
            }

        }

        return false
    }

    fun floatingPermissionEnabled() : Boolean {

        return Settings.canDrawOverlays(context)
    }

}

fun returnApi() : Int {

    return Build.VERSION.SDK_INT
}