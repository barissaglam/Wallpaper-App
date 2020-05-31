package barissaglam.client.wallpaperapp.data.remote.service

import barissaglam.client.wallpaperapp.data.remote.model.Photo
import barissaglam.client.wallpaperapp.data.remote.model.response.PhotosResponse
import okhttp3.ResponseBody
import retrofit2.http.*


interface RemoteApiService {
    @GET("search/photos")
    suspend fun getPhotosByQuery(@Query("query") query: String, @Query("page") page: Int, @Query("per_page") perPage: Int = 30): PhotosResponse

    @GET("photos/{id}")
    suspend fun getPhotoDetail(@Path("id") id: String): Photo

    @Streaming
    @GET
    suspend fun downloadImage(@Url url: String): ResponseBody
}