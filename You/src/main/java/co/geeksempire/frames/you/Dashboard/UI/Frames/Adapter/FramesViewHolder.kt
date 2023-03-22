/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/22/23, 5:06 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.UI.Frames.Adapter

import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.frames.you.databinding.FrameItemLayoutBinding

class FramesViewHolder (val rootView: FrameItemLayoutBinding) : RecyclerView.ViewHolder(rootView.root) {

    val rootViewItem = rootView.rootViewItem

    val frameBackground = rootView.frameBackground
    val frameForeground = rootView.frameForeground

}