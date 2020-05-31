package barissaglam.client.wallpaperapp.domain.usecase

import barissaglam.client.wallpaperapp.data.repository.RemoteRepository
import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import barissaglam.client.wallpaperapp.domain.mapper.PhotoDetailItemMapper
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.util.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoDetailUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val itemMapper: PhotoDetailItemMapper
) {
    fun getPhotoDetail(id: String): Flow<Resource<PhotoDetailViewItem>> {
        return remoteRepository.getPhotoDetail(id).map { resource ->
            resource.map { response ->
                itemMapper.mapFrom(response)
            }
        }
    }
}
