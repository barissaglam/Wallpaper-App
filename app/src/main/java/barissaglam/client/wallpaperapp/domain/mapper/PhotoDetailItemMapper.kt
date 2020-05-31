package barissaglam.client.wallpaperapp.domain.mapper

import barissaglam.client.wallpaperapp.base.extension.orEmpty
import barissaglam.client.wallpaperapp.data.remote.model.Photo
import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoDetailItemMapper @Inject constructor() : Mapper<Photo, PhotoDetailViewItem> {
    override fun mapFrom(item: Photo): PhotoDetailViewItem {
        return PhotoDetailViewItem(
            id = item.id.orEmpty(),
            source = item.links?.html.orEmpty(),
            photoOwnerName = item.user?.name.orEmpty(),
            photoOwnerProfile = item.user?.links?.html.orEmpty(),
            license = "Unsplash License",
            rawImageUrl = item.urls?.raw.orEmpty(),
            fullImageUrl = item.urls?.full.orEmpty(),
            regularImageUrl = item.urls?.regular.orEmpty(),
            smallImageUrl = item.urls?.small.orEmpty(),
            thumbImageUrl = item.urls?.thumb.orEmpty()
        )
    }
}