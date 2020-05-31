package barissaglam.client.wallpaperapp.presentation.detail

import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.util.Resource

suspend fun photoDetailPageCombiner(
    favoritePhotoList: Resource<List<PhotoViewItem>>,
    resource: Resource<PhotoDetailViewItem>
): PhotoDetailFragmentViewState {

    var favoriteList = emptyList<PhotoViewItem>()

    if (favoritePhotoList is Resource.Success) favoriteList = favoritePhotoList.data

    return PhotoDetailFragmentViewState(resource, favoriteList)

}
