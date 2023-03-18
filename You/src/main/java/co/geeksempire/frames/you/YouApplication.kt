/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/18/23, 9:59 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you

import android.app.Application
import android.os.Bundle
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics

class YouApplication : Application() {

    private val firebaseAnalytics: FirebaseAnalytics by lazy {
        FirebaseAnalytics.getInstance(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(applicationContext)

        firebaseAnalytics.logEvent(this@YouApplication.javaClass.simpleName, Bundle().apply { putString(this@YouApplication.javaClass.simpleName, "Started") })

    }

}