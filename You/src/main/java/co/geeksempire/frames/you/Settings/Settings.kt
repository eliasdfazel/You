/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/23/23, 10:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.R
import co.geeksempire.frames.you.Settings.Extensions.setupUserInterface
import co.geeksempire.frames.you.Utils.Settings.SystemSettings
import co.geeksempire.frames.you.databinding.SettingsLayoutBinding

class Settings : AppCompatActivity() {

    val systemSettings: SystemSettings by lazy {
        SystemSettings(applicationContext)
    }

    lateinit var settingLayoutBinding: SettingsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingLayoutBinding = SettingsLayoutBinding.inflate(layoutInflater)
        setContentView(settingLayoutBinding.root)

        setupUserInterface()


    }

    override fun onPause() {
        super.onPause()

        overridePendingTransition(0, R.anim.fade_out)

    }

}