/*
 * Copyright © 2023 By Geeks Empire.
 *
 * Created by Elias Fazel
 * Last modified 3/20/23, 5:07 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package co.geeksempire.frames.you.Dashboard.Frames.Adapter

import androidx.recyclerview.widget.RecyclerView
import co.geeksempire.frames.you.databinding.FrameItemLayoutBinding

class FramesViewHolder (val rootView: FrameItemLayoutBinding) : RecyclerView.ViewHolder(rootView.root) {

    val rootViewItem = rootView.rootViewItem

    val frameBackground = rootView.frameBackground
    val frameForeground = rootView.frameForeground

}