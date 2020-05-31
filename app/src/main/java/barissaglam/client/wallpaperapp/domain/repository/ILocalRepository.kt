package barissaglam.client.wallpaperapp.domain.repository

import barissaglam.client.wallpaperapp.data.local.entity.FavoritePhotoEntity
import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.view.categoryview.Category
import kotlinx.coroutines.flow.Flow

interface ILocalRepository {
    fun getCategories(): ArrayList<Category>

    suspend fun addFavorite(photoDetailViewItem: PhotoDetailViewItem)

    suspend fun deleteFavorite(photoId: String)

    fun getFavoriteList(): Flow<Resource<List<FavoritePhotoEntity>>>
}