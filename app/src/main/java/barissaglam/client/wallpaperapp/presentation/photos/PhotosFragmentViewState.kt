package barissaglam.client.wallpaperapp.presentation.photos

import barissaglam.client.wallpaperapp.base.domain.BaseFragmentViewState
import barissaglam.client.wallpaperapp.data.viewitem.PhotosViewItem
import barissaglam.client.wallpaperapp.util.Resource

class PhotosFragmentViewState(
    photosResource: Resource<PhotosViewItem>
) : BaseFragmentViewState<PhotosViewItem, Resource<PhotosViewItem>>(photosResource) {

}


