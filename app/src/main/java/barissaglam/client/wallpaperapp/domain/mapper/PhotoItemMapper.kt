package barissaglam.client.wallpaperapp.domain.mapper

import barissaglam.client.wallpaperapp.data.remote.model.response.PhotosResponse
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.data.viewitem.PhotosViewItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoItemMapper @Inject constructor() : Mapper<PhotosResponse, PhotosViewItem> {
    override fun mapFrom(item: PhotosResponse): PhotosViewItem {
        return PhotosViewItem(
            totalPage = item.totalPages,
            photos = item.results.map { photo ->
                PhotoViewItem(
                    id = photo.id.orEmpty(),
                    photoUrl = photo.urls?.small.toString()
                )
            }
        )
    }
}