/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/22/23, 6:09 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI.Frames.Preview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.geeksempire.frames.you.databinding.FramesPreviewLayoutBinding

class FramePreview : AppCompatActivity() {

    lateinit var framesPreviewLayoutBinding: FramesPreviewLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        framesPreviewLayoutBinding = FramesPreviewLayoutBinding.inflate(layoutInflater)
        setContentView(framesPreviewLayoutBinding.root)

        if (intent.hasExtra(Intent.EXTRA_TEXT)) {



        }
        /*Firebase.functions
            .getHttpsCallable("updateFrameTrends")
            .call(hashMapOf(
                "documentPath" to "/You/Frames/${displayRatio(context)}/${framesItems[position].frameName}",
                "frameTrend" to (framesItems[position].frameTrend + 1)
            ))*/

    }

}