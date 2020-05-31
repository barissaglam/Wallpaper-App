package barissaglam.client.wallpaperapp.data.repository

import barissaglam.client.wallpaperapp.data.local.dao.FavoritePhotoDao
import barissaglam.client.wallpaperapp.data.local.entity.FavoritePhotoEntity
import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import barissaglam.client.wallpaperapp.domain.repository.ILocalRepository
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.view.categoryview.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val favoritePhotoDao: FavoritePhotoDao
) : ILocalRepository {

    override fun getCategories(): ArrayList<Category> {
        return arrayListOf(
            Category("Wallpapers"),
            Category("Abstract"),
            Category("Animal"),
            Category("Art"),
            Category("Black"),
            Category("Car"),
            Category("Cat"),
            Category("City"),
            Category("Dog"),
            Category("Flowers"),
            Category("Funny"),
            Category("Sport"),
            Category("Summer"),
            Category("Winter")
        )
    }

    override suspend fun addFavorite(photoDetailViewItem: PhotoDetailViewItem) {
        favoritePhotoDao.addPhotoToFavorite(
            FavoritePhotoEntity(id = photoDetailViewItem.id, imageUrl = photoDetailViewItem.smallImageUrl)
        )
    }

    override suspend fun deleteFavorite(photoId: String) {
        return favoritePhotoDao.deletePhotoFromFavorite(photoId)
    }

    override fun getFavoriteList(): Flow<Resource<List<FavoritePhotoEntity>>> {
        return favoritePhotoDao.getFavoritePhotos().map {
            if (it.isNullOrEmpty()) Resource.Empty
            else Resource.Success(it)
        }.catch { Resource.Error(it) }
    }
}