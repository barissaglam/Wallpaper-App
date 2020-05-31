package barissaglam.client.wallpaperapp.domain.usecase

import barissaglam.client.wallpaperapp.data.repository.RemoteRepository
import barissaglam.client.wallpaperapp.data.viewitem.PhotosViewItem
import barissaglam.client.wallpaperapp.domain.mapper.PhotoItemMapper
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotosUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val itemMapper: PhotoItemMapper
) {
    fun getPhotosByQuery(query: String, page: Int): Flow<Resource<PhotosViewItem>> {
        return remoteRepository.getPhotosByQuery(query, page).map { resource ->
            resource.map { response ->
                itemMapper.mapFrom(response)
            }
        }
    }
}
