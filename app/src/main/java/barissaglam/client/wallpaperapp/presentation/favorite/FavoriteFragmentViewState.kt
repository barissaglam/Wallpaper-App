package barissaglam.client.wallpaperapp.presentation.favorite

import barissaglam.client.wallpaperapp.base.domain.BaseFragmentViewState
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.util.Resource

class FavoriteFragmentViewState(resource: Resource<List<PhotoViewItem>>) : BaseFragmentViewState<List<PhotoViewItem>, Resource<List<PhotoViewItem>>>(resource) {
}