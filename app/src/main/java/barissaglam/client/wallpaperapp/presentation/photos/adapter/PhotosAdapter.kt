package barissaglam.client.wallpaperapp.presentation.photos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.base.adapter.BaseViewHolder
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.databinding.ItemLoadMoreBinding
import barissaglam.client.wallpaperapp.databinding.ItemPhotoBinding
import barissaglam.client.wallpaperapp.presentation.common.holder.LoadMoreViewHolder
import barissaglam.client.wallpaperapp.presentation.common.holder.PhotoItemViewHolder
import barissaglam.client.wallpaperapp.util.PhotoItemDiffCallback

class PhotosAdapter : PagedListAdapter<PhotoViewItem, BaseViewHolder<*>>(PhotoItemDiffCallback.diffCallback) {
    /** Item Click Functions **/
    var onPhotoItemClick: ((PhotoViewItem) -> Unit)? = null

    enum class ItemType {
        TYPE_PHOTO {
            override fun onCreateViewHolder(parent: ViewGroup, layoutInflater: LayoutInflater, onPhotoItemClick: ((PhotoViewItem) -> Unit)?): BaseViewHolder<*> {
                val binding = DataBindingUtil.inflate<ItemPhotoBinding>(layoutInflater, R.layout.item_photo, parent, false)
                return PhotoItemViewHolder(binding, onPhotoItemClick)
            }
        },
        TYPE_LOAD_MORE {
            override fun onCreateViewHolder(parent: ViewGroup, layoutInflater: LayoutInflater, onPhotoItemClick: ((PhotoViewItem) -> Unit)?): BaseViewHolder<*> {
                val binding = DataBindingUtil.inflate<ItemLoadMoreBinding>(layoutInflater, R.layout.item_load_more, parent, false)
                return LoadMoreViewHolder(binding)
            }
        };

        abstract fun onCreateViewHolder(parent: ViewGroup, layoutInflater: LayoutInflater, onPhotoItemClick: ((PhotoViewItem) -> Unit)?): BaseViewHolder<*>
        fun viewType(): Int = ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemType.values()[viewType].onCreateViewHolder(parent, layoutInflater, onPhotoItemClick)
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount > 1 && itemCount == position + 1)
            ItemType.TYPE_LOAD_MORE.viewType()
        else ItemType.TYPE_PHOTO.viewType()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (getItemViewType(position) == ItemType.TYPE_LOAD_MORE.viewType()) {
                2
            } else {
                if (position % 3 == 0) 2 else 1
            }
        }
    }

    override fun getItemCount(): Int {
        return if (super.getItemCount() != 0) super.getItemCount() + 1 else 0
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PhotoItemViewHolder -> holder.bind(getItem(position))
        }
    }
}