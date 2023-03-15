/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 2/20/23, 6:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Utils.Views.Scrolls

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

fun snappedItemPosition(recyclerView: RecyclerView, snapHelper: SnapHelper) : Int {

    val layoutManager = recyclerView.layoutManager
    val snapView = snapHelper.findSnapView(layoutManager)

    return snapView?.let { layoutManager?.getPosition(it) } ?:0
}