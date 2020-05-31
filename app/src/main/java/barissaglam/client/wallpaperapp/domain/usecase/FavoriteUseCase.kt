package barissaglam.client.wallpaperapp.domain.usecase

import barissaglam.client.wallpaperapp.data.repository.LocalRepository
import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.domain.mapper.FavoriteItemMapper
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    private val itemMapper: FavoriteItemMapper
) {

    fun getFavoriteList(): Flow<Resource<List<PhotoViewItem>>> {
        return localRepository.getFavoriteList().map { resource ->
            resource.map { list ->
                list.map { entity ->
                    itemMapper.mapFrom(entity)
                }
            }
        }
    }

    suspend fun addFavorite(photoDetailViewItem: PhotoDetailViewItem) {
        localRepository.addFavorite(photoDetailViewItem)
    }

    suspend fun deleteFavorite(photoId: String) {
        localRepository.deleteFavorite(photoId = photoId)
    }
}