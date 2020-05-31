package barissaglam.client.wallpaperapp.util

import androidx.recyclerview.widget.DiffUtil
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem

object PhotoItemDiffCallback {
    val diffCallback = object : DiffUtil.ItemCallback<PhotoViewItem>() {
        override fun areItemsTheSame(oldItem: PhotoViewItem, newItem: PhotoViewItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PhotoViewItem, newItem: PhotoViewItem): Boolean = oldItem.id == newItem.id
    }
}