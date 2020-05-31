package barissaglam.client.wallpaperapp.domain.usecase

import barissaglam.client.wallpaperapp.data.repository.RemoteRepository
import barissaglam.client.wallpaperapp.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoDownloadUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository
) {
    fun downloadImage(url: String): Flow<Resource<ResponseBody>> {
        return remoteRepository.downloadImage(url)
    }
}
