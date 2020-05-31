package barissaglam.client.wallpaperapp.domain.mapper

import barissaglam.client.wallpaperapp.data.local.entity.FavoritePhotoEntity
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteItemMapper @Inject constructor() : Mapper<FavoritePhotoEntity, PhotoViewItem> {
    override fun mapFrom(item: FavoritePhotoEntity): PhotoViewItem {
        return PhotoViewItem(
            id = item.id,
            photoUrl = item.imageUrl
        )
    }
}