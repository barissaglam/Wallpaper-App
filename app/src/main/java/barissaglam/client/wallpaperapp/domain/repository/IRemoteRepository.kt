package barissaglam.client.wallpaperapp.domain.repository

import barissaglam.client.wallpaperapp.data.remote.model.Photo
import barissaglam.client.wallpaperapp.data.remote.model.response.PhotosResponse
import barissaglam.client.wallpaperapp.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface IRemoteRepository {
    fun getPhotosByQuery(query: String, page: Int): Flow<Resource<PhotosResponse>>
    fun getPhotoDetail(id: String): Flow<Resource<Photo>>
    fun downloadImage(url: String): Flow<Resource<ResponseBody>>
}