package barissaglam.client.wallpaperapp.presentation.common.holder

import barissaglam.client.wallpaperapp.base.adapter.BaseViewHolder
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.databinding.ItemPhotoBinding

class PhotoItemViewHolder(val binding: ItemPhotoBinding, private val onPhotoItemClick: ((PhotoViewItem) -> Unit)?) : BaseViewHolder<PhotoViewItem?>(binding.root) {

    override fun bind(data: PhotoViewItem?) {
        if (data != null) {
            binding.photo = data
            binding.root.setOnClickListener { onPhotoItemClick?.invoke(data) }
            binding.executePendingBindings()
        }

    }
}