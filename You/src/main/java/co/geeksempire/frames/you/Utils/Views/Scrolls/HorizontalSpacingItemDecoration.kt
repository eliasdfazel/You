/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/20/23, 7:01 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.Scrolls

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.frames.you.Utils.Display.dpToPixel
import kotlin.math.roundToInt

class HorizontalSpacingItemDecoration(context: Context, space: Int = 19) : RecyclerView.ItemDecoration() {

    private val spaceInDp = dpToPixel(context, space.toFloat())

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.right = spaceInDp.roundToInt()

    }
}