package barissaglam.client.wallpaperapp.data.repository

import barissaglam.client.wallpaperapp.data.remote.model.Photo
import barissaglam.client.wallpaperapp.data.remote.model.response.PhotosResponse
import barissaglam.client.wallpaperapp.data.remote.service.RemoteApiService
import barissaglam.client.wallpaperapp.domain.repository.IRemoteRepository
import barissaglam.client.wallpaperapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(private val api: RemoteApiService) : IRemoteRepository {

    override fun getPhotosByQuery(query: String, page: Int): Flow<Resource<PhotosResponse>> = flow {
        val responseCall = api.getPhotosByQuery(query, page)
        emit(Resource.Success(responseCall))
    }.execute()

    override fun getPhotoDetail(id: String): Flow<Resource<Photo>> = flow {
        val responseCall = api.getPhotoDetail(id)
        emit(Resource.Success(responseCall))
    }.execute()


    override fun downloadImage(url: String): Flow<Resource<ResponseBody>> = flow {
        val responseCall = api.downloadImage(url)
        emit(Resource.Success(responseCall))
    }.execute()


    // Common extension for each request
    private fun <T> Flow<Resource<T>>.execute(): Flow<Resource<T>> =
        onStart { emit(Resource.Loading) }
            .catch { throwable -> emit(Resource.Error(throwable)) }
            .flowOn(Dispatchers.IO)

}