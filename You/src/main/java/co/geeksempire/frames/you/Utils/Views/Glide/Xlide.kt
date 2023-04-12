/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 4/12/23, 12:28 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */
package co.geeksempire.frames.you.Utils.Views.Glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class Xlide : AppGlideModule() {

    override fun applyOptions(context: Context, glideBuilder: GlideBuilder) {
        super.applyOptions(context, glideBuilder)

        glideBuilder.apply {
            RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        }
    }

}