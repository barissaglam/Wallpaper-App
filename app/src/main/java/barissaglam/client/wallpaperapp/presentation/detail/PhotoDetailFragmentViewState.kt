package barissaglam.client.wallpaperapp.presentation.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.base.domain.BaseFragmentViewState
import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.util.State

class PhotoDetailFragmentViewState(
    detailResource: Resource<PhotoDetailViewItem>,
    private val favoritePhotoList: List<PhotoViewItem>
) : BaseFragmentViewState<PhotoDetailViewItem, Resource<PhotoDetailViewItem>>(detailResource) {

    fun viewsEnabled() = getState() == State.SUCCESS

    fun isFavorite(): Boolean = favoritePhotoList.map { it.id }.contains(getData()?.id)

    fun getFavoriteIcon(context: Context): Drawable? {
        return if (isFavorite())
            ContextCompat.getDrawable(context, R.drawable.ic_favorite_black_24dp)
        else
            ContextCompat.getDrawable(context, R.drawable.ic_favorite_border_black_24dp)
    }

    fun getFavoriteIconVisibility(): Int =
        when (getState()) {
            State.SUCCESS -> View.VISIBLE
            else -> View.GONE
        }
}


